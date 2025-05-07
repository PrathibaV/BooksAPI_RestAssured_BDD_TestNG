package com.books.helper;

public class TestContextManager {

    private static final ThreadLocal<TestContext> testContext = ThreadLocal.withInitial(TestContext::new);

    public static TestContext getContext() {
        return testContext.get();
    }

    public static void removeContext() {
        testContext.remove();
    }
}
