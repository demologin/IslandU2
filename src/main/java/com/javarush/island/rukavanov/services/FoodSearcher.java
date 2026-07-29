package com.javarush.island.rukavanov.services;


import com.javarush.island.rukavanov.config.FoodMatrix;
import com.javarush.island.rukavanov.config.OrganismType;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public final class FoodSearcher {

    private static final EnumMap<OrganismType, Map<OrganismType, Integer>> FOOD = new EnumMap<>(OrganismType.class);

    static {

        for (OrganismType hunter : OrganismType.animals()) {

            EnumMap<OrganismType, Integer> foodMap = new EnumMap<>(OrganismType.class);

            for (OrganismType food : OrganismType.values()) {

                int probability = FoodMatrix.getChance(hunter, food);

                if (probability > 0) {
                    foodMap.put(food, probability);
                }

            }

            FOOD.put(hunter, Collections.unmodifiableMap(foodMap)
            );
        }
    }


    public static Map<OrganismType, Integer> getAvailableFood(OrganismType hunter) {
        return FOOD.get(hunter);

    }

}
