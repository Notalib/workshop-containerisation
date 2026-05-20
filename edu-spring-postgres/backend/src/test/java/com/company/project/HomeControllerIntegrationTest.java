package com.company.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.EnabledIfDockerAvailable;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

@Testcontainers
@EnabledIfDockerAvailable
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HomeControllerIntegrationTest {

    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:18.3-alpine3.23");

    RestTestClient restTestClient;

    @BeforeEach
    public void setup(WebApplicationContext context) {
        restTestClient = RestTestClient.bindToApplicationContext(context).build();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> postgreSQLContainer.getJdbcUrl());
        registry.add("spring.datasource.driver-class-name", () -> postgreSQLContainer.getDriverClassName());
        registry.add("spring.datasource.username", () -> postgreSQLContainer.getUsername());
        registry.add("spring.datasource.password", () -> postgreSQLContainer.getPassword());
    }

    @Test
    void testHomeEndpoint() {
        String greeting = restTestClient.get()
          .uri("/")
          .exchange()
          .expectStatus().isOk()
          .expectBody(new ParameterizedTypeReference<String>() {})
          .returnResult()
          .getResponseBody();

        assertEquals("""
                     <!DOCTYPE HTML>
                     <html>
                     <head>
                       <title>Getting Started: Serving Web Content</title>
                       <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                       <style>body { margin: 40px; font-family: Roboto; font-size: 20px; }</style>
                     </head>
                     <body>
                       <p>Hello from Spring Boot in Docker!</p>
                       <p>Connected to database!</p>
                       <h2>Other pages:</h2>
                       <ul>
                         <li><a href="/greetings">All greetings</a></li>
                         <li><a href="/new">New greeting</a></li>
                       </ul>
                     </body>
                     """, greeting);

    }
}
