package com.test.reactivemongo.util;

import com.test.reactivemongo.ReactivemongoApplicationTests;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy;

public class TestContainersExtension implements BeforeAllCallback {
    @Override
    public void beforeAll(ExtensionContext context) {
        ReactivemongoApplicationTests.mongo.setWaitStrategy(new HostPortWaitStrategy());
        ReactivemongoApplicationTests.mongo.start();
    }
}
