package com.test.reactivemongo;

import com.mongodb.MongoClient;
import com.test.reactivemongo.util.TestContainersExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;

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

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues values = TestPropertyValues.of(
                    "spring.data.mongodb.host=" + mongo.getContainerIpAddress(),
                    "spring.data.mongodb.port=" + mongo.getMappedPort(27017)
            );
            values.applyTo(applicationContext);
        }
    }

    @AfterEach
    void setUp() {
        mongoClient.getDatabase(TEST_DB_NAME).drop();
    }

    @Test
    void contextLoads() {
    }
}
