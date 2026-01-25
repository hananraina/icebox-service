package com.icebox.service.config;

import com.icebox.service.service.TenantDiscoveryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TenantAuthenticationResolver implements AuthenticationManagerResolver<HttpServletRequest> {
    @Value("${app.jwk.uri}")
    private String jwkUri;
    private final TenantDiscoveryService tenantService;
    private final Map<String, AuthenticationManager> managers = new ConcurrentHashMap<>();

    public TenantAuthenticationResolver(TenantDiscoveryService tenantService) {
        this.tenantService = tenantService;
    }

    @Override
    public AuthenticationManager resolve(HttpServletRequest context) {
        String tenantId = tenantService.getTenantIdFromHostname(context.getServerName());
        return managers.computeIfAbsent(tenantId, this::buildManager);
    }
    private AuthenticationManager buildManager(String tenantId) {
        String finalJwkUri = jwkUri + "?tenantId=" + tenantId;
        return new JwtAuthenticationProvider(NimbusJwtDecoder.withJwkSetUri(finalJwkUri).build())::authenticate;
    }
}
