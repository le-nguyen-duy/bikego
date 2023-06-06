package com.example.bikego.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;

import java.util.List;

public class SwaggerUiConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        Contact contact = new Contact();
        contact.setEmail("duyle210101@gmail.com");
        contact.setName("Duy Le");

        Server localServer = new Server();
        localServer.setUrl("http://localhost:5500");
        localServer.setDescription("Server URL in Local environment");

        Server productionServer = new Server();
        productionServer.setUrl("https://bikego-production.up.railway.app");
        productionServer.setDescription("Server URL in Production environment");

        Info info = new Info()
                .title("BIKEGO MANAGER API")
                .contact(contact)
                .version("1.0")
                .description("Purpose For Education Only.");

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer, productionServer));
    }
}
