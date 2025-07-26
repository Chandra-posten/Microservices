package com.eduexcellence.oauth2server.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "OAuth2 Authorization Server API",
                version = "v1",
                description = "API documentation for OAuth2 Authorization Server"
        )
)
public class SwaggerConfig {
    // No custom configuration needed for basic setup
}

