package com.javarush.island.rukavanov.config;

import java.util.EnumMap;

public final class FoodMatrix {

    private static final EnumMap<OrganismType, EnumMap<OrganismType, Integer>> MATRIX = new EnumMap<>(OrganismType.class);

    static {

        for (OrganismType predator : OrganismType.values()) {
            MATRIX.put(predator, new EnumMap<>(OrganismType.class));
        }

        put(OrganismType.WOLF, OrganismType.HORSE, 10);
        put(OrganismType.WOLF, OrganismType.DEER, 15);
        put(OrganismType.WOLF, OrganismType.RABBIT, 60);
        put(OrganismType.WOLF, OrganismType.MOUSE, 80);
        put(OrganismType.WOLF, OrganismType.GOAT, 60);
        put(OrganismType.WOLF, OrganismType.SHEEP, 70);
        put(OrganismType.WOLF, OrganismType.BOAR, 15);
        put(OrganismType.WOLF, OrganismType.BUFFALO, 10);
        put(OrganismType.WOLF, OrganismType.DUCK, 40);

        put(OrganismType.SNAKE, OrganismType.FOX, 15);
        put(OrganismType.SNAKE, OrganismType.RABBIT, 20);
        put(OrganismType.SNAKE, OrganismType.MOUSE, 40);
        put(OrganismType.SNAKE, OrganismType.DUCK, 10);

        put(OrganismType.FOX, OrganismType.RABBIT, 70);
        put(OrganismType.FOX, OrganismType.MOUSE, 90);
        put(OrganismType.FOX, OrganismType.DUCK, 60);
        put(OrganismType.FOX, OrganismType.CATERPILLAR, 40);

        put(OrganismType.BEAR, OrganismType.SNAKE, 80);
        put(OrganismType.BEAR, OrganismType.HORSE, 40);
        put(OrganismType.BEAR, OrganismType.DEER, 80);
        put(OrganismType.BEAR, OrganismType.RABBIT, 80);
        put(OrganismType.BEAR, OrganismType.MOUSE, 90);
        put(OrganismType.BEAR, OrganismType.GOAT, 70);
        put(OrganismType.BEAR, OrganismType.SHEEP, 70);
        put(OrganismType.BEAR, OrganismType.BOAR, 50);
        put(OrganismType.BEAR, OrganismType.BUFFALO, 20);
        put(OrganismType.BEAR, OrganismType.DUCK, 10);

        put(OrganismType.EAGLE, OrganismType.FOX, 10);
        put(OrganismType.EAGLE, OrganismType.RABBIT, 90);
        put(OrganismType.EAGLE, OrganismType.MOUSE, 90);
        put(OrganismType.EAGLE, OrganismType.DUCK, 80);

        put(OrganismType.HORSE, OrganismType.PLANT, 100);
        put(OrganismType.DEER, OrganismType.PLANT, 100);
        put(OrganismType.RABBIT, OrganismType.PLANT, 100);
        put(OrganismType.MOUSE, OrganismType.PLANT, 100);
        put(OrganismType.GOAT, OrganismType.PLANT, 100);
        put(OrganismType.SHEEP, OrganismType.PLANT, 100);
        put(OrganismType.BOAR, OrganismType.MOUSE, 50);
        put(OrganismType.BOAR, OrganismType.PLANT, 100);
        put(OrganismType.BUFFALO, OrganismType.PLANT, 100);
        put(OrganismType.DUCK, OrganismType.CATERPILLAR, 90);
        put(OrganismType.DUCK, OrganismType.PLANT, 100);
        put(OrganismType.CATERPILLAR, OrganismType.PLANT, 100);

    }

    private FoodMatrix() {

    }

    private static void put(OrganismType hunter,
                            OrganismType food,
                            int chance) {

        MATRIX.get(hunter).put(food, chance);

    }

    public static int getChance(OrganismType hunter,
                                OrganismType food) {

        return MATRIX.get(hunter).getOrDefault(food, 0);

    }

}
