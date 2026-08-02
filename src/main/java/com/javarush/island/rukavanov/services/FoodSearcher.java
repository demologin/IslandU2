package com.javarush.island.rukavanov.services;


import com.javarush.island.rukavanov.config.FoodMatrix;
import com.javarush.island.rukavanov.config.OrganismType;
import com.javarush.island.rukavanov.entity.Organism;
import com.javarush.island.rukavanov.entity.animals.Animal;
import com.javarush.island.rukavanov.entity.island.Location;
import com.javarush.island.rukavanov.util.RandomUtil;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public final class FoodSearcher {

    private static final EnumMap<OrganismType, Map<OrganismType, Integer>> FOOD =

            new EnumMap<>(OrganismType.class);

    static {

        for (OrganismType hunter : OrganismType.animals()) {

            EnumMap<OrganismType, Integer> foodMap =

                    new EnumMap<>(OrganismType.class);

            for (OrganismType food : OrganismType.values()) {

                int chance = FoodMatrix.getChance(hunter, food);

                if (chance > 0) {

                    foodMap.put(food, chance);

                }

            }

            FOOD.put(hunter, Collections.unmodifiableMap(foodMap));

        }

    }

    private FoodSearcher() {

    }

    public static Map<OrganismType, Integer> getAvailableFood(

            OrganismType hunter) {

        return FOOD.get(hunter);

    }

    /**

     * Возвращает случайную подходящую добычу,

     * находящуюся в данной клетке.

     */

    public static Organism findFood(Location location,

                                    Animal hunter) {

        Map<OrganismType, Integer> availableFood =

                FOOD.get(hunter.getData());

        if (availableFood == null || availableFood.isEmpty()) {

            return null;

        }

        OrganismType[] foodTypes =

                availableFood.keySet().toArray(new OrganismType[0]);

        while (foodTypes.length > 0) {

            OrganismType type =

                    foodTypes[RandomUtil.random(foodTypes.length)];

            Organism food =

                    location.getRandomOrganism(type);

            if (food != null) {

                return food;

            }

            foodTypes = remove(foodTypes, type);

        }

        return null;

    }

    private static OrganismType[] remove(OrganismType[] array,

                                         OrganismType value) {

        return java.util.Arrays.stream(array)

                .filter(type -> type != value)

                .toArray(OrganismType[]::new);

    }

}
