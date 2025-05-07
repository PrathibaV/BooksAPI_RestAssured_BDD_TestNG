package com.books.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class ScenarioCounterManager {
    private static final AtomicInteger orderCreationCounter = new AtomicInteger(0);

    public static int getNextIndex() {
        return orderCreationCounter.getAndIncrement();
    }

    public static void resetCounter() {
        orderCreationCounter.set(0);
    }
}
