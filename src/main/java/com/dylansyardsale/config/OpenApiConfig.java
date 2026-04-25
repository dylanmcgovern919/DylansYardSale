package com.dylansyardsale.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Tells Spring this class provides configuration beans
public class OpenApiConfig {

    @Bean // Registers custom OpenAPI metadata for Swagger UI
    OpenAPI customOpenAPI() { 
        return new OpenAPI()
                .info(new Info()
                        .title("Dylan's Yard Sale") // Title shown in Swagger UI
                        .version("1.0.0") 
                        .description("API for Dylan's Yard Sale")); 
    }
}