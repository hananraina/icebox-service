package com.icebox.service.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class SecurityUtils {
    public record CurrentUser(String userId, String tenantId) {}

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
                    jwt.getToken().getSubject(),
                    jwt.getToken().getClaimAsString("tid") // tenantId
            );
        }
        return null;
    }
}
