package com.javarush.island.zimina.animalcrossing.model.animals.omnivores;


import com.javarush.island.zimina.animalcrossing.config.AnimalSpeciesConfig;

import java.util.Map;

public class Mouse extends Omnivore {

    public Mouse(int x, int y, AnimalSpeciesConfig config) {
        super(x, y, config);
    }

    @Override
    public Map<String, Integer> getFoodProbabilities() {
        return Map.of("Caterpillar",90);
    }

}
