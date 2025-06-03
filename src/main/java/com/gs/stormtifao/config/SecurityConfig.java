package com.gs.stormtifao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/css/**", "/style.css", "/logo.png", "/images/**", "/fragments/**", "/login", "/error", "/static/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(request -> request.getRequestURI().equals("/logout") && "GET".equals(request.getMethod()))
                        .logoutSuccessUrl("/")
                        .permitAll()
                );
        return http.build();
    }
}