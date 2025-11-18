package com.chatr.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedHeaders("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedOrigins(
                                "http://localhost:8081",
                                "http://localhost:19006",
                                "http://192.168.1.10:8081",
                                "http://192.168.1.10:19006"
                        )
//                        .allowedOriginPatterns("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}
