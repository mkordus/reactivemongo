package com.test.reactivemongo.handler;

import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

abstract class Handler<T, R> {

    Mono<ServerResponse> buildResponseOr404(Mono<T> entity) {

        return entity.
            flatMap(obj -> {
                try {
                    return ok().body(Mono.just(getDtoClassName().getConstructor(getEntityClassName()).newInstance(obj)),
                        getDtoClassName());
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                    InvocationTargetException e) {
                    throw new RuntimeException("Can't create object of class " + getDtoClassName().toString()
                        + ". There is no entity based constructor", e);
                }
            }).switchIfEmpty(notFound().build());
    }

    private Class<T> getEntityClassName() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private Class<T> getDtoClassName() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
}
