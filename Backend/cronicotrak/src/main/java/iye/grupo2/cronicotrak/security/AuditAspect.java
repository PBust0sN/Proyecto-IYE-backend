package iye.grupo2.cronicotrak.security;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AuditAspect {

    private final SecurityLogService securityLogService;

    public AuditAspect(SecurityLogService securityLogService) {
        this.securityLogService = securityLogService;
    }

    @Before("@annotation(logSecurity)")
    public void logSecurityEvent(JoinPoint joinPoint, LogSecurity logSecurity) {
        String username = "anonymous";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof Jwt) {
                Jwt jwt = (Jwt) authentication.getPrincipal();
                username = jwt.getClaimAsString("preferred_username");
                if (username == null) {
                    username = jwt.getSubject();
                }
            } else {
                username = authentication.getName();
            }
        }

        String ip = "unknown";
        HttpServletRequest request = null;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            request = attributes.getRequest();
            ip = request.getRemoteAddr();
            String forwardedFor = request.getHeader("X-Forwarded-For");
            if (forwardedFor != null && !forwardedFor.isEmpty()) {
                ip = forwardedFor.split(",")[0].trim();
            }
        }

        String details = "Method invoked: " + joinPoint.getSignature().toShortString();
        
        securityLogService.logEvent(username, logSecurity.action(), ip, logSecurity.severity(), details, false);
    }
}
