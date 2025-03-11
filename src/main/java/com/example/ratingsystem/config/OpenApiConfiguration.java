package com.example.ratingsystem.config;

import com.example.ratingsystem.domain.dtos.error.ApiError;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Schema;
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

    private Schema getSchema(Class<?> schemaClass) {
        var schema = ModelConverters.getInstance()
                .read(schemaClass).get(schemaClass.getSimpleName());
        if (schema == null) {
            throw new IllegalStateException("Schema for " + schemaClass.getSimpleName() + " is empty");
        }
        return schema;
    }

    private void customizePathItem(PathItem pathItem, Schema schema) {
        pathItem.readOperations().forEach(operation ->
                operation.getResponses().forEach((status, response) -> {
                    if (status.startsWith("4") || status.startsWith("5")) {
                        response.getContent().forEach((code, mediaType) -> mediaType.setSchema(schema));
                    }
                })
        );
    }
}