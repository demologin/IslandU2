package com.javarush.island.rukavanov.processor;

import com.javarush.island.rukavanov.config.Settings;
import com.javarush.island.rukavanov.entity.animals.Animal;
import com.javarush.island.rukavanov.entity.island.Island;
import com.javarush.island.rukavanov.entity.island.Location;

public class HungerProcessor {
    public void process(Location location, Island island) {

        for (Animal animal : location.getAllAnimalsSnapshot()) {

            animal.loseSatiety();
//            animal.loseSatiety(animal.getData().getFoodNorm() * Settings.SATIETY_LOSS_PERCENT
//            );

            if (animal.getSatiety() <= 0) {

                animal.die();

            }

        }

    }
}
