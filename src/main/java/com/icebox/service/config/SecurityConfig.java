package com.icebox.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.jwt.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, TenantAuthenticationResolver resolver, CustomAuthenticationEntryPoint entryPoint) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable()) // Disable CSRF for APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**")
                        .authenticated()
                        .anyRequest()
                        .permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .authenticationManagerResolver(resolver)
                        .authenticationEntryPoint(entryPoint)
                );

        return http.build();
    }
}
