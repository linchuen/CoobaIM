package com.cooba.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocConfig {
    @Bean
    public OpenAPI springOpenApi() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Authorization"))
                .components(
                        new Components()
                                .addSecuritySchemes("Authorization",
                                        new SecurityScheme()
                                                .name("User Token")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .in(SecurityScheme.In.HEADER)
                                                .bearerFormat("JWT")
                                )
                )
                .info(new Info()
                        .title("CoobaIM Doc")
                        .version("1.0.0")
                        .license(new License()
                                .name("許可協議")
                                .url("https://www.apache.org/licenses/LICENSE-2.0"))
                );
    }
}
