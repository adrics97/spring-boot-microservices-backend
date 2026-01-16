package com.example.orders_service.config;

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

import java.util.List;

@Configuration
public class OpenApiConfig {

    private static final String SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI ordersApiOpenAPI() {
        return new OpenAPI()
                // 👉 Swagger SIEMPRE ejecuta contra el gateway
                .servers(List.of(new Server().url("http://localhost:8080")))
                .info(new Info()
                        .title("Orders API")
                        .description("REST API for managing orders.")
                        .version("1.0.0"))
                .addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }

    // 👉 Reescribe /api/orders/** → /orders/**
    @Bean
    public OpenApiCustomizer ordersPathsCustomizer() {
        return openApi -> {
            if (openApi.getPaths() == null) return;

            Paths newPaths = new Paths();

            openApi.getPaths().forEach((path, item) -> {
                String newPath = path.replaceFirst("^/api/orders", "/orders");
                newPaths.addPathItem(newPath, item);
            });

            openApi.setPaths(newPaths);
        };
    }
}
