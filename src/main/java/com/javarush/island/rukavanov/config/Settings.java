package com.javarush.island.rukavanov.config;

public final class Settings {

    private Settings() {

    }

    public static final int ISLAND_WIDTH = 100;
    public static final int ISLAND_HEIGHT = 20;

    public static final int TICK_DELAY = 1000;
    public static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    public static final int START_WOLF = 5;
    public static final int START_SNAKE = 2;
    public static final int START_FOX = 4;
    public static final int START_BEAR = 1;
    public static final int START_EAGLE = 2;
    public static final int START_HORSE = 4;
    public static final int START_DEER = 5;
    public static final int START_RABBIT = 20;
    public static final int START_MOUSE = 30;
    public static final int START_GOAT = 10;
    public static final int START_SHEEP = 10;
    public static final int START_BOAR = 8;
    public static final int START_BUFFALO = 3;
    public static final int START_DUCK = 15;
    public static final int START_CATERPILLAR = 50;
    public static final int START_PLANT = 100;

    // Процент от нормы еды, который животное теряет за такт.
    public static final double HUNGER_PERCENT = 0.10;
    public static final int PLANTS_GROW_PER_TICK = 5;

    public static final int BABIES_PER_BIRTH = 1;

    public static final int MIN_ANIMALS_TO_CONTINUE = 0;

}


