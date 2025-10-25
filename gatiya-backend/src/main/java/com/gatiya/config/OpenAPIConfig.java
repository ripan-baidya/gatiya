package com.gatiya.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * This class provides configuration for OpenAPI.
 *
 * You can access the Swagger UI at {@link http://localhost:8080/swagger-ui/index.html}
 * The OpenAPI JSON (machine-readable) is available at {@link http://localhost:8080/v3/api-docs}
 */
@Configuration
@Profile({"dev"})
public class OpenAPIConfig {

    @Bean
    public OpenAPI gatiyaOpenAPI() {
        return new OpenAPI()
                // Information about the API
                .info(new Info()
                        .title("Gatiya Ride Sharing Application")
                        .description("API Documentation for Gatiya Ride Sharing Application")
                        .version("1.0.0")
                        // Contact Info will update later
                        .contact(new Contact()
                                .name("Gatiya Ride Sharing")
                                .url("https://gatiya.com")
                                .email("official@orbitdynamix.com")
                        )
                        // Licence info will update later
                        .license(new License()
                                .name("Gatiya Ride Sharing")
                                .url("https://gatiya.com")
                        )
                );
                // JWT security for Swagger
//                .components(new Components()
//                        .addSecuritySchemes("bearer-key",
//                                new SecurityScheme()
//                                        .type(SecurityScheme.Type.HTTP)
//                                        .scheme("bearer")
//                                        .bearerFormat("JWT")
//                        )
//                )
//                .addSecurityItem(new SecurityRequirement().addList("bearer-key"));

    }

    // ******** Grouped OpenAPI ********
    @Bean
    public GroupedOpenApi testApi() {
        return GroupedOpenApi.builder()
                .group("Test")
                .pathsToMatch("/tests/**")
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("User")
                .pathsToMatch("/users/**")
                .build();
    }

    // TODO: more grouped APIs will be added here, later
}
