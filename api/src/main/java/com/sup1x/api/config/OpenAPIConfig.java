package com.sup1x.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

        @Value("${homepage.openAPI.title}")
        private String title;

        @Value("${homepage.openAPI.description}")
        private String description;

        @Value("${homepage.openAPI.version}")
        private String version;

        @Value("${homepage.openAPI.termsOfServiceUrl}")
        private String termsOfServiceUrl;

        @Value("${homepage.openAPI.server.dev.description}")
        private String devServerDescription;

        @Value("${homepage.openAPI.server.dev.url}")
        private String devServerUrl;

        @Value("${homepage.openAPI.server.prod.description}")
        private String prodServerDescription;

        @Value("${homepage.openAPI.server.prod.url}")
        private String prodServerUrl;

        @Value("${homepage.openAPI.license.name}")
        private String licenseName;

        @Value("${homepage.openAPI.license.url}")
        private String licenseUrl;

        @Value("${homepage.openAPI.contact.name}")
        private String contactName;

        @Value("${homepage.openAPI.contact.url}")
        private String contactUrl;

        @Value("${homepage.openAPI.contact.email}")
        private String contactEmail;

        @Value("${homepage.openAPI.api-docs.enabled}")
        private boolean apiDocsEnabled;

        @Value("${homepage.openAPI.api-docs.path}")
        private String apiDocsPath;

        @Value("${homepage.openAPI.swagger-ui.enabled}")
        private boolean swaggerUiEnabled;

        @Value("${homepage.openAPI.swagger-ui.path}")
        private String swaggerUiPath;

        @Value("${homepage.openAPI.swagger-ui.tryItOutEnabled}")
        private boolean tryItOutEnabled;

        @Value("${homepage.openAPI.swagger-ui.operationsSorter}")
        private String operationsSorter;

        @Value("${homepage.openAPI.swagger-ui.tagsSorter}")
        private String tagsSorter;

        @Value("${homepage.openAPI.swagger-ui.filter}")
        private boolean filter;

        @Bean
        public OpenAPI myOpenAPI() {

                Server devServer = new Server();
                devServer.setUrl(devServerUrl);
                devServer.setDescription(devServerDescription);

                Server prodServer = new Server();
                prodServer.setUrl(prodServerUrl);
                prodServer.setDescription(prodServerDescription);

                Contact contact = new Contact();
                contact.setEmail(contactEmail);
                contact.setName(contactName);
                contact.setUrl(contactUrl);

                License mitLicense = new License().name(licenseName).url(licenseUrl);

                Info info = new Info()
                        .title(title)
                        .version(version)
                        .contact(contact)
                        .description(description).termsOfService(termsOfServiceUrl)
                        .license(mitLicense);

                return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
        }
}
