package com.example.EasyWalletAPI.shared.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("*"));  // Permitir todas las solicitudes de cualquier origen
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));  // Permitir todos los métodos
        config.setAllowedHeaders(List.of("*"));  // Permitir todos los headers
        config.setAllowCredentials(true);  // Permitir credenciales (si es necesario)

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}