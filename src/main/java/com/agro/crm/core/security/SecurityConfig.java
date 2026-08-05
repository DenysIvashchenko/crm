package com.agro.crm.core.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, @Lazy JwtAuthenticationFilter jwtAuthFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/users/**").hasAuthority("ADMIN")
                        .requestMatchers( "/api/farmers/**").hasAnyAuthority("ADMIN", "MANAGER")
                        .requestMatchers( "/api/fields/**").hasAnyAuthority("ADMIN", "MANAGER","AGRONOMIST")
                        .requestMatchers( "/api/crops/**").hasAnyAuthority("ADMIN", "MANAGER","AGRONOMIST")
                        .requestMatchers( "/api/tasks/**").hasAnyAuthority("ADMIN", "MANAGER","AGRONOMIST","OPERATOR")
                        .requestMatchers( "/api/equipment/**").hasAnyAuthority("ADMIN", "MANAGER","AGRONOMIST","OPERATOR")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("Authentication failed: " + authException.getMessage());
                        })
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
