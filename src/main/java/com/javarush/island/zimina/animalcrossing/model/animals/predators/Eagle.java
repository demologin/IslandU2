package com.javarush.island.zimina.animalcrossing.model.animals.predators;

import com.javarush.island.zimina.animalcrossing.config.AnimalSpeciesConfig;

import java.util.Map;

public class Eagle extends Predator {

    public Eagle(int x, int y, AnimalSpeciesConfig config) {
        super(x, y, config);
    }

    @Override
    public Map<String, Integer> getFoodProbabilities() {
        return Map.of("Fox", 10, "Rabbit", 90, "Mouse", 90);
    }

}
