package com.javarush.island.zelenin.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    private RandomUtils() {
        throw new UnsupportedOperationException(); //TODO message
    }

    public static ThreadLocalRandom instance() {
        return ThreadLocalRandom.current();
    }

    public static int nextInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static double nextDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static boolean rollChance(int chance) {
        return ThreadLocalRandom.current().nextInt(100) < chance;
    }

    public static boolean rollChance(double chance) {
        return ThreadLocalRandom.current().nextDouble() * 100 < chance;
    }
}
