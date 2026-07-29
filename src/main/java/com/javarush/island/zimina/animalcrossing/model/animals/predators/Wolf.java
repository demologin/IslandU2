package com.javarush.island.zimina.animalcrossing.model.animals.predators;

import com.javarush.island.zimina.animalcrossing.config.AnimalSpeciesConfig;

import java.util.Map;

public class Wolf extends Predator {

    public Wolf(int x, int y, AnimalSpeciesConfig config) {
        super(x,y, config);
    }

    @Override
    public Map<String, Integer> getFoodProbabilities() {
        return Map.of("Horse", 10,
                "Deer", 15,
                "Rabbit", 60,
                "Mouse", 80,
                "Goat", 60,
                "Sheep", 70,
                "Boar", 15,
                "Buffalo", 10,
                "Duck", 40
        );
    }
}
