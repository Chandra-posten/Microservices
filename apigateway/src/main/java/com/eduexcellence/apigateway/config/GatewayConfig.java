package com.eduexcellence.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Student Service Routes
                .route("student-service", r -> r.path("/api/students/**")
                        .uri("lb://studentms"))

                // Fee Management Service Routes
                .route("fee-management-service", r -> r.path("/api/fees/**")
                        .uri("lb://fee-management-ms"))

                // Eureka Server Routes
                .route("eureka-web", r -> r.path("/eureka/web")
                        .filters(f -> f.setPath("/"))
                        .uri("http://localhost:8761"))

                .route("eureka-resources", r -> r.path("/eureka/**")
                        .uri("http://localhost:8761"))

                // Health Check Routes for individual services
                .route("student-health", r -> r.path("/health/students/**")
                        .filters(f -> f.stripPrefix(2))
                        .uri("lb://studentms"))

                .route("fee-health", r -> r.path("/health/fees/**")
                        .filters(f -> f.stripPrefix(2))
                        .uri("lb://fee-management-ms"))

                .build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOriginPatterns(Arrays.asList("*"));
        corsConfig.setMaxAge(3600L);
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowedHeaders(Arrays.asList("*"));
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}
