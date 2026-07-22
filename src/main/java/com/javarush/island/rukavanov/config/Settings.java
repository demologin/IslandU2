package com.javarush.island.rukavanov.config;

public final class Settings {
    private Settings() {
    }

    public static final int ISLAND_WIDTH = 100;
    public static final int ISLAND_HEIGHT = 20;
    public static final int TICK_TIME = 1000;
    public static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    public static final int START_PLANTS = 50;

}

