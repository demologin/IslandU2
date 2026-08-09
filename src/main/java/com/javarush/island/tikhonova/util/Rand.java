package com.javarush.island.tikhonova.util;

import java.util.concurrent.ThreadLocalRandom;

public class Rand {
    private Rand() {
    }

    public static int random(int min, int max) {
        return min >= max ? min : ThreadLocalRandom.current().nextInt(min, max);
    }

    public static double random(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static boolean gat(double percentProbably) {
        return random(0d, 100d) < percentProbably;
    }
}
