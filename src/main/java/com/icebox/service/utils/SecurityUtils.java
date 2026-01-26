package com.icebox.service.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.UUID;

public class SecurityUtils {
    public record CurrentUser(UUID userId, String tenantId) {}

    public static String getClaim(String claimName) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthenticationToken jwtAuth) {
            return jwtAuth.getToken().getClaimAsString(claimName);
        }
        return null;
    }

    public static CurrentUser getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthenticationToken jwt) {
            return new CurrentUser(
                    UUID.fromString(jwt.getToken().getSubject()),
                    jwt.getToken().getClaimAsString("tid") // tenantId
            );
        }
        return null;
    }
}
