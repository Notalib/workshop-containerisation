package com.company.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
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
class HealthControllerIntegrationTest {

    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:18.3-alpine3.23");

    RestTestClient restTestClient;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> postgreSQLContainer.getJdbcUrl());
        registry.add("spring.datasource.driver-class-name", () -> postgreSQLContainer.getDriverClassName());
        registry.add("spring.datasource.username", () -> postgreSQLContainer.getUsername());
        registry.add("spring.datasource.password", () -> postgreSQLContainer.getPassword());
    }

    @BeforeEach
    public void setup(WebApplicationContext context) {
        restTestClient = RestTestClient.bindToApplicationContext(context).build();
    }

    @Test
    void testHealthEndpoint() {
        restTestClient.get().uri("/healthz").exchange()
            .expectStatus().isOk()
            .expectBody(String.class).isEqualTo("healthy");
    }

    @Test
    void testReadyEndpoint() {
        restTestClient.get().uri("/readyz").exchange()
            .expectStatus().isOk()
            .expectBody(String.class).isEqualTo("ready");
    }
}
