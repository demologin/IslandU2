package com.javarush.island.zimina.animalcrossing.model.plant;

import com.javarush.island.zimina.animalcrossing.config.PlantConfig;
import com.javarush.island.zimina.animalcrossing.map.Location;
import com.javarush.island.zimina.animalcrossing.model.Organism;
import com.javarush.island.zimina.animalcrossing.util.ProbabilityUtils;

public class Plant  extends Organism {

    private static final double GROWTH_RATE = 50;
    private static final int SPAWN_CHANCE = 80;

    public Plant(int x, int y, double weight) {
        super(x, y, weight);
    }

    public void reduceWeight(double amount) {
        weight = Math.max(weight - amount, 0);
    }

    public void grow(Location location, PlantConfig config) {
        double maxWeight = config.getWeight();
        weight = Math.min(weight + maxWeight * GROWTH_RATE / 100, maxWeight);

        if (location.getPlantsCount() < config.getMaxPerCell() && ProbabilityUtils.rollChance(SPAWN_CHANCE)) {
            Plant offspring = new Plant(getX(), getY(), maxWeight);
            location.getLock().lock();
            try {
                location.getPlants().add(offspring);
            } finally {
                location.getLock().unlock();
            }
        }
    }
}
