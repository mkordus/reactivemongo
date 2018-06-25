package com.test.reactivemongo.util;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy;

public class TestContainersExtension implements BeforeAllCallback {

    private static GenericContainer mongo = new GenericContainer("mongo:3.4.0").withExposedPorts(27017);

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

    @Override
    public void beforeAll(ExtensionContext context) {
        mongo.setWaitStrategy(new HostPortWaitStrategy());
        mongo.start();
    }
}
