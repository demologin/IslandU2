package com.javarush.island.rukavanov.entity.animals.predators;

import com.javarush.island.rukavanov.config.AnimalData;
import com.javarush.island.rukavanov.entity.animals.Animal;

public abstract class Predator extends Animal {

    protected Predator(AnimalData data) {
        super(data);
    }

}
