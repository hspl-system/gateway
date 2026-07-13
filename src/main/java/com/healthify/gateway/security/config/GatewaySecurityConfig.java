package com.healthify.gateway.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class GatewaySecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers( "/public/**").permitAll()
                        .anyRequest().authenticated()
                )

                // Browser Login
                .oauth2Login(Customizer.withDefaults())

                // JWT validation
                .oauth2ResourceServer(resource ->
                        resource.jwt(Customizer.withDefaults())
                );

        return http.build();
    }
}
