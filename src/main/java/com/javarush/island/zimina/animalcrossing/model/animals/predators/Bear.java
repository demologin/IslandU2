package com.javarush.island.zimina.animalcrossing.model.animals.predators;

import com.javarush.island.zimina.animalcrossing.config.AnimalSpeciesConfig;

import java.util.Map;

public class Bear extends Predator {

    public Bear(int x, int y, AnimalSpeciesConfig config) {
        super(x, y, config);
    }

    @Override
    public Map<String, Integer> getFoodProbabilities() {
        return Map.of("Boa", 80, "Horse", 40, "Deer", 80, "Rabbit", 80, "Mouse", 90, "Goat", 70, "Sheep", 70, "Boar", 50, "Buffalo", 20, "Duck", 10);
    }

}
