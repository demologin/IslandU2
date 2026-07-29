package com.javarush.island.zimina.animalcrossing.model.animals.herbivores;

import com.javarush.island.zimina.animalcrossing.config.AnimalSpeciesConfig;
import com.javarush.island.zimina.animalcrossing.map.Location;
import com.javarush.island.zimina.animalcrossing.model.animals.Animal;
import com.javarush.island.zimina.animalcrossing.model.plant.Plant;
import com.javarush.island.zimina.animalcrossing.util.ProbabilityUtils;


/**
 * Базовый класс для всех травоядных животных на острове.
 */
public abstract class Herbivore extends Animal {

    private static final int PLANT_EATING_CHANCE = 100;

    protected Herbivore(int x, int y, AnimalSpeciesConfig config) {
        super(x, y, config);
    }

    @Override
    public void eat(Location location) {
        if (!location.getPlants().isEmpty()) {
            if (ProbabilityUtils.rollChance(PLANT_EATING_CHANCE)) {
                Plant plant = location.getPlants().get(0);
                double amountToEat = Math.min(getFoodNeeded() - getSatiety(), plant.getWeight());
                plant.reduceWeight(amountToEat);
                increaseSatiety(amountToEat);
                if (plant.getWeight() == 0) {
                    location.getPlants().remove(plant);
                }
            }
        }
    }
}


