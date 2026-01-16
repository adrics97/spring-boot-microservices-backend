package com.example.users_api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    String schemeName = "bearerAuth";

    @Bean
    public OpenAPI userApiOpenAPI(){
        return new OpenAPI()
                .addServersItem(new Server().url("http://localhost:8080"))
                .info(new Info()
                        .title("Users API")
                        .description("REST API for managing users (Spring Boot + JPA). ")
                        .version("1.0.0"))
                .addSecurityItem(new SecurityRequirement().addList(schemeName))
                .components(new Components()
                        .addSecuritySchemes(schemeName,
                                new SecurityScheme()
                                        .name(schemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );

    }

    @Bean
    public OpenApiCustomizer usersPathsCustomizer() {
        return openApi -> {
            if (openApi.getPaths() == null) return;

            Paths newPaths = new Paths();

            openApi.getPaths().forEach((path, item) -> {
                // /api/users/**  →  /users/**
                String newPath = path.replaceFirst("^/api/users", "/users");
                newPaths.addPathItem(newPath, item);
            });

            openApi.setPaths(newPaths);
        };
    }

}
