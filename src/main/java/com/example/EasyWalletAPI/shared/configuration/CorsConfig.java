package com.example.EasyWalletAPI.shared.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permitir todos los orígenes con patrones
        config.setAllowedOriginPatterns(Arrays.asList("*")); // Cambiar setAllowedOrigins a setAllowedOriginPatterns
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
        config.setAllowedHeaders(Arrays.asList("*")); // Permitir todos los headers
        config.setAllowCredentials(true); // Permitir credenciales

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}