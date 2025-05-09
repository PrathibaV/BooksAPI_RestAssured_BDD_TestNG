package com.books.context;

public class ScenarioContextManager {

    private static final ThreadLocal<ScenarioContext> scenarioContext = ThreadLocal.withInitial(ScenarioContext::new);

    public static ScenarioContext getContext() {
        return scenarioContext.get();
    }

    public static void removeContext() {
    	scenarioContext.remove();
    }
}
