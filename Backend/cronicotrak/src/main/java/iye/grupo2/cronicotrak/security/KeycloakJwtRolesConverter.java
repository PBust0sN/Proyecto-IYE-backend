package iye.grupo2.cronicotrak.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Extrae los roles del claim {@code realm_access.roles} del JWT emitido
 * por Keycloak y los convierte en {@link GrantedAuthority} con prefijo
 * {@code ROLE_} para que sean compatibles con {@code hasRole()} de Spring.
 *
 * <p>Ejemplo de claim en el JWT de Keycloak:</p>
 * <pre>
 * "realm_access": {
 *   "roles": ["PACIENTE", "ENFERMERO", "MEDICO", "ADMIN"]
 * }
 * </pre>
 */
@Component
public class KeycloakJwtRolesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    /** Nombre del claim raíz que Keycloak incluye en el JWT. */
    private static final String REALM_ACCESS_CLAIM = "realm_access";

    /** Nombre del sub-claim que contiene la lista de roles del realm. */
    private static final String ROLES_CLAIM = "roles";

    /** Prefijo requerido por Spring Security para reconocer una autoridad como rol. */
    private static final String ROLE_PREFIX = "ROLE_";

    @Override
    @SuppressWarnings("unchecked")
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaimAsMap(REALM_ACCESS_CLAIM);

        if (realmAccess == null || !realmAccess.containsKey(ROLES_CLAIM)) {
            return Collections.emptyList();
        }

        List<String> roles = (List<String>) realmAccess.get(ROLES_CLAIM);

        return roles.stream()
                .filter(role -> role != null && !role.isBlank())
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.toUpperCase()))
                .collect(Collectors.toList());
    }
}
