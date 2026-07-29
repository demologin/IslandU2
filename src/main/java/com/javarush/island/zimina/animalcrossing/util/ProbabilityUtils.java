package com.javarush.island.zimina.animalcrossing.util;


import java.util.concurrent.ThreadLocalRandom;


public class ProbabilityUtils {
    private  ProbabilityUtils() {
        // класс не предназначен для создания экземпляров
    }

    public static boolean rollChance(int probabilityPercent) {
        int roll = ThreadLocalRandom.current().nextInt(100);
        return roll < probabilityPercent;
    }
}
