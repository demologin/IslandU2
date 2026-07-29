package com.javarush.island.zimina.animalcrossing.model.animals.predators;

import com.javarush.island.zimina.animalcrossing.config.AnimalSpeciesConfig;

import java.util.Map;

public class Boa extends Predator {

    public Boa(int x, int y, AnimalSpeciesConfig config) {
        super(x, y, config);
    }

    @Override
    public Map<String, Integer> getFoodProbabilities() {
        return Map.of("Rabbit", 20, "Mouse", 40, "Duck", 10);
    }

}
