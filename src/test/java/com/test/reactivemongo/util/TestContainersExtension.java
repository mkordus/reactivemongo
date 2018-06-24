package com.test.reactivemongo.util;

import com.test.reactivemongo.ReactivemongoApplicationTests;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestContainersExtension implements BeforeAllCallback, AfterAllCallback {
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        ReactivemongoApplicationTests.mongo.start();
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
//        ReactivemongoApplicationTests.mongo.stop();
    }
}
