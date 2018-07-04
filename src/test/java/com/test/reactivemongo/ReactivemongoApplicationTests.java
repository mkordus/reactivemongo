package com.test.reactivemongo;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

class ReactivemongoApplicationTests extends IntegrationTest {

    @Test
    void putAndGetTest() {
        final String uri = "/note/123";
        final String noteBody = "{\"content\":\"test\"}";

        webTestClient.get().uri(uri)
                .exchange()
                .expectStatus().isNotFound();

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
