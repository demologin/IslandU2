package com.javarush.island.rukavanov.services;

import com.javarush.island.rukavanov.config.OrganismType;
import com.javarush.island.rukavanov.config.Settings;
import com.javarush.island.rukavanov.entity.Organism;
import com.javarush.island.rukavanov.entity.animals.Animal;
import com.javarush.island.rukavanov.entity.island.Island;
import com.javarush.island.rukavanov.entity.island.Location;

public class HungerService extends AbstractIslandService {

    @Override
    protected void processLocation(Location location, Island island) {

        for (OrganismType type : OrganismType.animals()) {
            for (Animal animal : location.getAnimals(type)) {
                animal.loseSatiety(animal.getData().getFoodNorm() * Settings.SATIETY_LOSS_PERCENT
                );
            }
        }
        location.removeDeadAnimals();
    }
}
