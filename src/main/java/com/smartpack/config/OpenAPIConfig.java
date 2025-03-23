package com.smartpack.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPIConfig
 * configuració de documentacio de enpoints
 */
@Configuration
public class OpenAPIConfig {

    /**
     * Inicialitza Apidoc
     * 
     * @return OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Smartpack API")
                        .version("1.0")
                        .description("Documentació de la API de Smartpack"));
    }

    /**
     * Agrupació de documentació
     * 
     * @return GroupedOpenApi
     */
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }
}
