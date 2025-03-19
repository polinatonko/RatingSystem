package com.example.ratingsystem.config;

import com.example.ratingsystem.domain.dtos.error.ApiError;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            var errorSchema = getSchema(ApiError.class);
            openApi.getPaths().values().forEach(item -> customizePathItem(item, errorSchema));
        };
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPISecurityScheme()))
                .info(new Info()
                        .title("Rating System REST API")
                        .version("1.0"));
    }

    private Schema<?> getSchema(Class<?> schemaClass) {
        var schema = ModelConverters.getInstance()
                .read(schemaClass).get(schemaClass.getSimpleName());
        if (schema == null) {
            throw new IllegalStateException("Schema for " + schemaClass.getSimpleName() + " is empty");
        }
        return schema;
    }

    /**
     * Sets schema for error responses with 4XX and 5XX status codes.
     *
     * @param pathItem path item to modify
     * @param schema error response schema
     */
    private void customizePathItem(PathItem pathItem, Schema<?> schema) {
        pathItem.readOperations().forEach(operation ->
                operation.getResponses().forEach((status, response) -> {
                    if (status.startsWith("4") || status.startsWith("5")) {
                        var content = response.getContent();
                        if (content != null) {
                            content.forEach((code, mediaType) -> mediaType.setSchema(schema));
                        }
                    }
                })
        );
    }

    private SecurityScheme createAPISecurityScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}