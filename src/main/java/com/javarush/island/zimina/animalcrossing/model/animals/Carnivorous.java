package com.javarush.island.zimina.animalcrossing.model.animals;

import com.javarush.island.zimina.animalcrossing.util.ProbabilityUtils;

import java.util.Map;

public interface Carnivorous {

    Map<String, Integer> getFoodProbabilities();

    default boolean tryToEat(String preyType) {
        Integer probability = getFoodProbabilities().get(preyType);
        if (probability == null) {
            return false;
        }
        return ProbabilityUtils.rollChance(probability);
    }
}
