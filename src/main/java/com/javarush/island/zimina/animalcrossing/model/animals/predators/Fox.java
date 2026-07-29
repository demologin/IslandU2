package com.javarush.island.zimina.animalcrossing.model.animals.predators;

import com.javarush.island.zimina.animalcrossing.config.AnimalSpeciesConfig;

import java.util.Map;

public class Fox extends Predator {

    public Fox(int x, int y, AnimalSpeciesConfig config) {
        super(x, y, config);
    }

    @Override
    public  Map<String, Integer> getFoodProbabilities() {
        return Map.of("Rabbit", 70, "Mouse", 90, "Duck", 10, "Caterpillar", 40);
    }

}
