package com.apromore.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class OpenApiConfig {

    @Bean
    public RouterFunction<ServerResponse> serveOpenApiYaml() {
        Resource openApiYaml = new ClassPathResource("openapi.yaml");
        return RouterFunctions.route(RequestPredicates.GET("/v3/api-docs.yaml"),
                request -> ServerResponse.ok().body(openApiYaml));
    }
}