package com.casadocodigo.productapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI productApiOpenApi() {
        return new OpenAPI()
            .info(new Info()
                .title("Product API")
                .description("REST API for product catalog data")
                .version("v1")
                .contact(new Contact().name("Paulo")));
    }
}
