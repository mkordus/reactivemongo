package com.test.reactivemongo.config;

import com.test.reactivemongo.handler.NoteHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class Routes {
    private final NoteHandler noteHandler;

    @Bean
    public RouterFunction<ServerResponse> router() {
        //kotlin dsl looks much better :(
        return route(
                GET("/note/{noteId}"),
                noteHandler::getNote
        ).andRoute(
                PUT("/note/{noteId}").and(contentType(MediaType.APPLICATION_JSON_UTF8)).and(accept(MediaType.APPLICATION_JSON_UTF8)),
                noteHandler::updateNote
        );
    }
}
