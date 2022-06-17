package com.mipsas.poko.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    public static final String BEARER_AUTH_HEADER = "Bearer";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(BEARER_AUTH_HEADER, new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme(BEARER_AUTH_HEADER)
                                .in(SecurityScheme.In.HEADER)
                                .name(BEARER_AUTH_HEADER)))
                .addSecurityItem(new SecurityRequirement().addList(BEARER_AUTH_HEADER))
                .info(new Info()
                        .title("Poko-Game API Documentation")
                        .description("This page has been generated automatically, based on the provided Poko-Game service API")
                        .version("1.0"));
    }
}
