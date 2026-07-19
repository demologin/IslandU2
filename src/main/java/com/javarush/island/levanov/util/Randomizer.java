package com.javarush.island.levanov.util;

import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {
    private Randomizer() {
    }

    public static double getRandomProbability() {
        return (double) ThreadLocalRandom.current().nextInt(101) / 100;
    }

    public static int getRandomNextCell(int availableCellsNumber) {
        return ThreadLocalRandom.current().nextInt(availableCellsNumber);
    }
}
