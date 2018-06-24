package com.test.reactivemongo;

import com.test.reactivemongo.util.TestContainersExtension;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;

@ExtendWith(TestContainersExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
@ContextConfiguration(initializers = ReactivemongoApplicationTests.Initializer.class)
public class ReactivemongoApplicationTests {

	public static GenericContainer mongo = new GenericContainer("mongo:3.4.0").withExposedPorts(27017);

	public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			TestPropertyValues values = TestPropertyValues.of(
					"spring.mongo.host=" + mongo.getContainerIpAddress(),
					"spring.mongo.port=" + mongo.getMappedPort(27017)
			);
			values.applyTo(applicationContext);
		}
	}

	@Test
	public void contextLoads() {
	}
}
