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

        config.addAllowedOrigin("*");  // Permitir todas las solicitudes de cualquier origen
        config.addAllowedMethod("*");  // Permitir todos los métodos (GET, POST, etc.)
        config.addAllowedHeader("*");  // Permitir todos los headers

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}