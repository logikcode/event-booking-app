package com.bw.reference.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("BW FullStack Reference Application API").version("1.0")
                        .contact(contact())
                        .description(
                                "The BW Reference RESTful service using springdoc-openapi and OpenAPI 3."));
    }

    private Contact contact() {
        Contact contact = new Contact();
        contact.setName("Byteworks Bot");
        contact.setEmail("developers@byteworks.com.ng");
        return contact;
    }
}
