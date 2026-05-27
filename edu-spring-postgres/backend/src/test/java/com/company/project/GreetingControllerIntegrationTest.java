package com.company.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.EnabledIfDockerAvailable;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

@Testcontainers
@EnabledIfDockerAvailable
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GreetingControllerIntegrationTest {

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
    void testHomeEndpoint() {
        String html = restTestClient.get().uri("/").exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .returnResult().getResponseBody();

        Document document = Jsoup.parse(html);
        assertEquals(1, document.select("h1:contains(Greeting)").size());
        assertEquals(1, document.select("h1:contains(Other pages)").size());
        assertEquals(1, document.select("a[href=/greetings]").size());
        assertEquals(1, document.select("a[href=/new]").size());
        assertEquals(1, document.select("p:contains(Hello from Spring Boot)").size());
        assertEquals(1, document.select("p:contains(Connected to database)").size());
        assertEquals(1, document.select("p:contains(" + postgreSQLContainer.getJdbcUrl() + ")").size());
    }

    @Test
    void testGreetingsEndpoint() {
        String html = restTestClient.get().uri("/greetings").exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .returnResult().getResponseBody();

        Document document = Jsoup.parse(html);
        assertEquals(1, document.select("h1:contains(Greetings)").size());
        assertTrue(document.select("table tbody tr").size() >= 3);
        assertTrue(document.select("table tbody tr td a:contains(Docker container)").size() == 1);
        assertTrue(document.select("table tbody tr td a:contains(An awesome Workshop)").size() == 1);
        assertTrue(document.select("table tbody tr td a:contains(The Future)").size() == 1);
    }

    @Test
    void testNewGreetingEndpoint() {
        String html = restTestClient.get().uri("/new").exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .returnResult().getResponseBody();
        Document formPageDocument = Jsoup.parse(html);

        assertTrue(formPageDocument.select("form#new-greeting").size() == 1);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        String newGreetingName = "Integration Test Greeting";
        form.add("name", newGreetingName);

        RestTestClient.ResponseSpec createResponse = restTestClient
                .post()
                .uri("/greetings")
                .body(form)
                .exchange()
                .expectStatus()
                .is3xxRedirection();

        if (createResponse.returnResult().getResponseHeaders().getLocation().getPath() != null) {
            String createdGreetingPath = createResponse
                    .returnResult()
                    .getResponseHeaders()
                    .getLocation()
                    .getPath();

            assertTrue(createdGreetingPath.matches(
                    "/greetings/[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"));

            RestTestClient.ResponseSpec createdGreetingResponse = restTestClient
                    .get()
                    .uri(createdGreetingPath)
                    .exchange()
                    .expectStatus()
                    .isOk();

            assertNotNull(createdGreetingResponse.returnResult().toString());

            Document createdGreetingResponseDocument = Jsoup.parse(createdGreetingResponse
                    .expectStatus().isOk()
                    .expectBody(String.class)
                    .returnResult().getResponseBody());

            assertEquals(
                    1,
                    createdGreetingResponseDocument
                            .select("p:contains(" + newGreetingName + ")")
                            .size());
        }

        RestTestClient.ResponseSpec greetingsResponse =
                restTestClient.get().uri("/greetings").exchange().expectStatus().isOk();

        assertNotNull(greetingsResponse.returnResult().toString());

        Document greetingsResponseDocument = Jsoup.parse(greetingsResponse
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult().getResponseBody());

        assertEquals(
                1,
                greetingsResponseDocument
                        .select("table tbody tr td a:contains(" + newGreetingName + ")")
                        .size());
    }
}
