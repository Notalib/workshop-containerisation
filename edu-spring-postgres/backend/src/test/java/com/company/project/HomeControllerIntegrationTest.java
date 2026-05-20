package com.company.project;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.EnabledIfDockerAvailable;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@EnabledIfDockerAvailable
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HomeControllerIntegrationTest {

    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:12-alpine");

    @Autowired
    private TestRestTemplate restTemplate;

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> postgreSQLContainer.getJdbcUrl());
        registry.add("spring.datasource.driver-class-name", () -> postgreSQLContainer.getDriverClassName());
        registry.add("spring.datasource.username", () -> postgreSQLContainer.getUsername());
        registry.add("spring.datasource.password", () -> postgreSQLContainer.getPassword());
    }

    @Test
    void testHomeEndpoint() {
        ResponseEntity<String> response = restTemplate.getForEntity("/", String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
        assertTrue(response.getBody().contains(postgreSQLContainer.getJdbcUrl()));
    }

    @Test
    void testGreetingsEndpoint() {
        ResponseEntity<String> response = restTemplate.getForEntity("/greetings", String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
        Document document = Jsoup.parse(response.getBody());
        assertTrue(document.select("h1:contains(Greetings)").size() == 1);
        assertTrue(document.select("table tbody tr").size() >= 3);
        assertTrue(document.select("table tbody tr td a:contains(Docker)").size() == 1);
        assertTrue(document.select("table tbody tr td a:contains(Workshop)").size() == 1);
        assertTrue(document.select("table tbody tr td a:contains(The Future)").size() == 1);
    }

    @Test
    void testNewGreetingEndpoint() {
        ResponseEntity<String> response = restTemplate.getForEntity("/new", String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
        Document formPageDocument = Jsoup.parse(response.getBody());
        assertTrue(formPageDocument.select("form#new-greeting").size() == 1);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        String newGreetingName = "Integration Test Greeting";
        form.add("name", newGreetingName);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);
        ResponseEntity<String> createResponse = restTemplate.postForEntity("/greetings", request, String.class);

        assertTrue(createResponse.getStatusCode().is2xxSuccessful() || createResponse.getStatusCode().is3xxRedirection());
        if (createResponse.getHeaders().getLocation() != null) {
            String createdGreetingPath = createResponse.getHeaders().getLocation().toString();
            assertTrue(createdGreetingPath.matches("/greetings/\\d+"));

            ResponseEntity<String> createdGreetingResponse = restTemplate.getForEntity(createdGreetingPath, String.class);
            assertTrue(createdGreetingResponse.getStatusCode().is2xxSuccessful());
            Assertions.assertNotNull(createdGreetingResponse.getBody());
            assertTrue(createdGreetingResponse.getBody().contains(newGreetingName));
        }

        ResponseEntity<String> greetingsResponse = restTemplate.getForEntity("/greetings", String.class);
        assertTrue(greetingsResponse.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(greetingsResponse.getBody());
        assertTrue(greetingsResponse.getBody().contains(newGreetingName));

    }

}
