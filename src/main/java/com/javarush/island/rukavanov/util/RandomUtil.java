package com.javarush.island.rukavanov.util;

import java.util.concurrent.ThreadLocalRandom;

public final class RandomUtil {
    private RandomUtil() {

    }

    public static int random(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }

    public static int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static boolean chance(int percent) {
        return ThreadLocalRandom.current().nextInt(100) < percent;
    }
}
