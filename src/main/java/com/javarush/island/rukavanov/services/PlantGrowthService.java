package com.javarush.island.rukavanov.services;

import com.javarush.island.rukavanov.config.OrganismType;
import com.javarush.island.rukavanov.config.Settings;
import com.javarush.island.rukavanov.entity.island.Island;
import com.javarush.island.rukavanov.entity.island.Location;
import com.javarush.island.rukavanov.factory.OrganismFactory;
import com.javarush.island.rukavanov.util.RandomUtil;

public class PlantGrowthService {
    public void process(Island island) {

        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                growPlants(island.get(x, y));
            }
        }

    }

    private void growPlants(Location location) {

        location.lock();

        try {

            int currentPlants = location.count(OrganismType.PLANT);
            int freePlaces = OrganismType.PLANT.getMaxCount() - currentPlants;

            if (freePlaces <= 0) {
                return;
            }

            int newPlants = Math.min(RandomUtil.random(Settings.PLANT_GROWTH_PER_TICK + 1), freePlaces);

            for (int i = 0; i < newPlants; i++) {
                location.addOrganism(OrganismFactory.create(OrganismType.PLANT));
            }

        } finally {
            location.unlock();
        }

    }
}
