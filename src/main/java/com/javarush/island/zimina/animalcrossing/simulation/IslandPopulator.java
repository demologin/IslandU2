package com.javarush.island.zimina.animalcrossing.simulation;

import com.javarush.island.zimina.animalcrossing.config.*;
import com.javarush.island.zimina.animalcrossing.map.Island;
import com.javarush.island.zimina.animalcrossing.map.Location;
import com.javarush.island.zimina.animalcrossing.model.animals.Animal;
import com.javarush.island.zimina.animalcrossing.model.plant.Plant;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class IslandPopulator {

    private final AnimalFactory animalFactory = new AnimalFactory();

    private final IslandConfig islandConfig = ConfigLoader.loadConfig();

    private int getRandomX() {
        return ThreadLocalRandom.current().nextInt(SimulationConfig.ISLAND_WIDTH);
    }

    private int getRandomY() {
        return ThreadLocalRandom.current().nextInt(SimulationConfig.ISLAND_HEIGHT);
    }

    public void populate(Island island) {
        for (Map.Entry<String, AnimalSpeciesConfig> entry : islandConfig.getAnimals().entrySet()) {
            String type = entry.getKey();
            AnimalSpeciesConfig config = entry.getValue();
            int count = config.getInitialCount();

            for (int i = 0; i < count; i++) {
              int x = getRandomX();
              int y = getRandomY();
              Animal animal = animalFactory.createAnimal(type, x, y, config);
              if (animal != null) {
                Location location = island.getLocation(x, y);

                if(location.countAnimalsOfType(type) < config.getMaxPerCell()) {
                    location.getAnimals().add(animal);
                }
              }
            }
        }

        PlantConfig plantConfig = islandConfig.getPlant();
        int plantCount = plantConfig.getInitialCount();

        for (int i = 0; i < plantCount; i++) {
            int x = getRandomX();
            int y = getRandomY();
            Location location = island.getLocation(x, y);

            if (location.getPlantsCount() < plantConfig.getMaxPerCell()) {
                Plant plant = new Plant(x, y, plantConfig.getWeight());
                location.getPlants().add(plant);
            }
        }
    }
}
