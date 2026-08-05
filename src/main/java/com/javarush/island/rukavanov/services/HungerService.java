package com.javarush.island.rukavanov.services;

import com.javarush.island.rukavanov.entity.animals.Animal;
import com.javarush.island.rukavanov.entity.island.Island;
import com.javarush.island.rukavanov.entity.island.Location;

public class HungerService {
    public void process(Location location, Island island) {

        for (Animal animal : location.getAllAnimalsSnapshot()) {

            animal.loseSatiety();

            if (animal.getSatiety() <= 0) {
                animal.die();
            }
        }
    }
}
