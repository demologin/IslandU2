package com.javarush.island.rukavanov.services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ExecutorManager {

    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static ExecutorService getExecutor() {
        return EXECUTOR;
    }

    public static void shutdown() {
        EXECUTOR.shutdown();
    }

}
