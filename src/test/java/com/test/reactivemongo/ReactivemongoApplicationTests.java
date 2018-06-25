package com.test.reactivemongo;

import com.mongodb.MongoClient;
import com.test.reactivemongo.util.TestContainersExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.GenericContainer;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@ExtendWith(TestContainersExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
@ContextConfiguration(initializers = ReactivemongoApplicationTests.Initializer.class)
public class ReactivemongoApplicationTests {

    private static final String TEST_DB_NAME = "test";
    public static GenericContainer mongo = new GenericContainer("mongo:3.4.0").withExposedPorts(27017);

    @Autowired
    private MongoClient mongoClient;

    @Autowired
    WebTestClient webTestClient;

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            TestPropertyValues values = TestPropertyValues.of(
                    "spring.data.mongodb.host=" + mongo.getContainerIpAddress(),
                    "spring.data.mongodb.port=" + mongo.getMappedPort(27017)
            );
            values.applyTo(applicationContext);
        }
    }

    @BeforeEach
    void setUp() {
        mongoClient.getDatabase(TEST_DB_NAME).drop();
    }

    @Test
    void putAndGetTest() {
        final String uri = "/note/123";
        final String noteBody = "{\"content\":\"test\"}";

        webTestClient.get().uri(uri)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.content", "");

        webTestClient.put().uri(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(noteBody), String.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.content", "test");

        webTestClient.get().uri(uri)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.content", "test");
    }
}
