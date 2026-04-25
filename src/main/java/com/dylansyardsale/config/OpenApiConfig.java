package com.dylansyardsale.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI dylansYardSaleOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Dylan's Yard Sale")
                        .description("Spring Boot REST API for products, tags, and orders.")
                        .version("v1"));
    }
}
