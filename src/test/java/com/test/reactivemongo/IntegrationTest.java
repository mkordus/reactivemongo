package com.test.reactivemongo;

import com.mongodb.MongoClient;
import com.test.reactivemongo.util.TestContainersExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(TestContainersExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
@ContextConfiguration(initializers = TestContainersExtension.Initializer.class)
public abstract class IntegrationTest {

    private static final String TEST_DB_NAME = "test";

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MongoClient mongoClient;

    @Autowired
    protected WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        mongoClient.getDatabase(TEST_DB_NAME).drop();
    }
}
