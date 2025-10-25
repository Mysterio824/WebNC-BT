package com.webnc.bt.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Sakila API",
                version = "1.0",
                description = "API for managing Films and Actors in the Sakila database.",
                license = @License(name = "MIT License", url = "https://opensource.org/licenses/MIT")
        )
)
public class OpenApiConfig {}