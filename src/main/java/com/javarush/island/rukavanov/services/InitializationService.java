package com.javarush.island.rukavanov.services;

import com.javarush.island.rukavanov.config.OrganismType;
import com.javarush.island.rukavanov.config.Settings;
import com.javarush.island.rukavanov.entity.island.Island;
import com.javarush.island.rukavanov.entity.island.Location;
import com.javarush.island.rukavanov.factory.OrganismFactory;
import com.javarush.island.rukavanov.util.RandomUtil;

public class InitializationService {
    public void initialize(Island island) {

        for (int y = 0; y < island.getHeight(); y++) {

            for (int x = 0; x < island.getWidth(); x++) {

                Location location = island.get(x, y);

                populate(location, OrganismType.WOLF, Settings.START_WOLF);
                populate(location, OrganismType.SNAKE, Settings.START_SNAKE);
                populate(location, OrganismType.FOX, Settings.START_FOX);
                populate(location, OrganismType.BEAR, Settings.START_BEAR);
                populate(location, OrganismType.EAGLE, Settings.START_EAGLE);
                populate(location, OrganismType.HORSE, Settings.START_HORSE);
                populate(location, OrganismType.DEER, Settings.START_DEER);
                populate(location, OrganismType.RABBIT, Settings.START_RABBIT);
                populate(location, OrganismType.MOUSE, Settings.START_MOUSE);
                populate(location, OrganismType.GOAT, Settings.START_GOAT);
                populate(location, OrganismType.SHEEP, Settings.START_SHEEP);
                populate(location, OrganismType.BOAR, Settings.START_BOAR);
                populate(location, OrganismType.BUFFALO, Settings.START_BUFFALO);
                populate(location, OrganismType.DUCK, Settings.START_DUCK);
                populate(location, OrganismType.CATERPILLAR, Settings.START_CATERPILLAR);
                populate(location, OrganismType.PLANT, Settings.START_PLANT);

            }

        }
    }

    private void populate(Location location,
                          OrganismType type,
                          int maxCount) {

        int count = RandomUtil.random(maxCount + 1);

        for (int i = 0; i < count; i++) {
            location.addOrganism(OrganismFactory.create(type)
            );
        }
    }
}
