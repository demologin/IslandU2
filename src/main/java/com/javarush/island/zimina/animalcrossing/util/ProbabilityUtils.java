package com.javarush.island.zimina.animalcrossing.util;


import java.util.concurrent.ThreadLocalRandom;

/**
 * Утилитный класс для работы со случайными вероятностными событиями.
 * Используется везде, где нужно проверить "происходит ли событие
 * с заданной вероятностью" (поедание, размножение, выбор направления и т.д.).
 */

public class ProbabilityUtils {
    private  ProbabilityUtils() {
        // класс не предназначен для создания экземпляров
    }

    /**
     * Проверяет, происходит ли событие с указанной вероятностью.
     *
     * @param probabilityPercent вероятность события в процентах (0-100)
     * @return true, если событие произошло
     */

    public static boolean rollChance(int probabilityPercent) {
        int roll = ThreadLocalRandom.current().nextInt(100);
        return roll < probabilityPercent;
    }
}
