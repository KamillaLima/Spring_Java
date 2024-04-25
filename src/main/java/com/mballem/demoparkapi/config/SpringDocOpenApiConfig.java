package com.mballem.demoparkapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//Vai ser uma classe de configuração do Swagger
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(
                        new Info()
                                .title("REST API - SPRING PARK")
                                .description("API para gestão de estacionamento de veiculos")
                                .version("v1")
                                .contact(new Contact().name("Kamilla Lima").email("kamillalima868@gmail.com"))
                );
    }
}
