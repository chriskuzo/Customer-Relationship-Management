package com.mahwi.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Global application-level configuration.
 * Defines CORS, global beans, and shared application configuration.
 */
@Configuration
public class ApplicationConfig {

    /**
     * Defines global CORS policy for all endpoints.
     * Allows Swagger, local frontend, and production origins.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // 🧠 Update these origins based on your frontend deployment:
        config.setAllowedOrigins(List.of(
                "http://localhost:3000",  // React local dev
                "http://localhost:8080",  // Swagger UI
                "https://your-production-domain.com"
        ));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
