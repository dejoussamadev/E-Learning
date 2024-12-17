package com.tek_up.gestionStock.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**") // Allow CORS for all paths
                .allowedOrigins("*") // Allow all origins or specify allowed ones
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow methods
                .allowedHeaders("*") // Allow all headers or specific headers
                .exposedHeaders("Authorization") // Expose authorization header for JWT token
                .maxAge(3600L) // Cache CORS pre-flight response for 1 hour
                .allowCredentials(true); // Allow credentials (important for JWT)
    }
}
