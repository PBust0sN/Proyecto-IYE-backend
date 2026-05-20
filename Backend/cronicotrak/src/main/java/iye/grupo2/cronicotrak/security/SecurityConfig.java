package iye.grupo2.cronicotrak.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Configuración de seguridad de la aplicación.
 *
 * <ul>
 *   <li>API REST stateless (sin sesiones HTTP).</li>
 *   <li>CSRF deshabilitado (el frontend usa JWT, no cookies de sesión).</li>
 *   <li>Todos los endpoints bajo {@code /api/**} requieren autenticación.</li>
 *   <li>Los roles se extraen del claim {@code realm_access.roles} de Keycloak.</li>
 *   <li>CORS configurado para permitir el origen del frontend React.</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity          // habilita @PreAuthorize, @PostAuthorize en controladores
@RequiredArgsConstructor
public class SecurityConfig {

    private final KeycloakJwtRolesConverter keycloakJwtRolesConverter;

    /** URL del JWKS de Keycloak (inyectada desde application.yaml). */
    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    /** Orígenes permitidos para CORS (inyectados desde application.yaml / .env). */
    @Value("${app.cors.allowed-origins:http://localhost:5173}")
    private String allowedOrigins;

    // ─────────────────────────────────────────────────────────────────────────
    // SecurityFilterChain
    // ─────────────────────────────────────────────────────────────────────────

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Sin sesiones HTTP — cada request se autentica con el JWT
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // CSRF deshabilitado: la API usa JWT (stateless), no cookies de sesión
            .csrf(AbstractHttpConfigurer::disable)

            // Configuración CORS — el origen React está en allowedOrigins
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // Reglas de autorización por ruta
            .authorizeHttpRequests(auth -> auth
                // Actuator health: público para load-balancers / k8s probes
                .requestMatchers("/actuator/health").permitAll()
                // Todos los demás endpoints requieren rol ADMIN o USER
                .anyRequest().hasAnyRole("ADMIN", "USER")
            )

            // Configurar como Resource Server que valida JWTs
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .decoder(jwtDecoder())
                    .jwtAuthenticationConverter(jwtAuthenticationConverter())
                )
            );

        return http.build();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // JWT Decoder — usa la JWKS URI de Keycloak para verificar firmas
    // ─────────────────────────────────────────────────────────────────────────

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // JWT Authentication Converter
    // Combina el converter de roles de Keycloak con el converter estándar de Spring
    // ─────────────────────────────────────────────────────────────────────────

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        // Converter estándar de Spring (lee claim "scope" / "scp")
        JwtGrantedAuthoritiesConverter defaultConverter = new JwtGrantedAuthoritiesConverter();
        defaultConverter.setAuthorityPrefix("SCOPE_");

        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();

        // Delegamos la extracción de authorities a nuestro converter de Keycloak
        // que lee realm_access.roles y les agrega el prefijo ROLE_
        authenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var keycloakRoles = keycloakJwtRolesConverter.convert(jwt);
            var scopeAuthorities = defaultConverter.convert(jwt);
            var all = new java.util.ArrayList<>(keycloakRoles != null ? keycloakRoles : List.of());
            if (scopeAuthorities != null) all.addAll(scopeAuthorities);
            return all;
        });

        // El claim "preferred_username" de Keycloak se usa como nombre del principal
        authenticationConverter.setPrincipalClaimName("preferred_username");

        return authenticationConverter;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // CORS — permite al frontend React comunicarse con la API
    // ─────────────────────────────────────────────────────────────────────────

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Orígenes permitidos (ej: http://localhost:5173)
        List<String> origins = Arrays.asList(allowedOrigins.split(","));
        config.setAllowedOrigins(origins);

        // Métodos HTTP permitidos
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        // Headers que el frontend puede enviar (incluye Authorization para el JWT)
        config.setAllowedHeaders(List.of(
            "Authorization",
            "Content-Type",
            "Accept",
            "X-Requested-With",
            "Cache-Control"
        ));

        // Permite al cliente leer headers de la respuesta (ej: Location en 201 Created)
        config.setExposedHeaders(List.of("Location"));

        // Permite cookies / credenciales (necesario si el frontend usa cookies de sesión de Keycloak)
        config.setAllowCredentials(true);

        // Cachear la respuesta preflight OPTIONS por 1 hora
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
