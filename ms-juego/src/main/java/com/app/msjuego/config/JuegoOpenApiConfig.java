package com.app.msjuego.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JuegoOpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Microservicio Juegos")
                                .version("1.0")
                                .description("Gestión de juegos, géneros, estudios y plataformas")
                );
    }
}
