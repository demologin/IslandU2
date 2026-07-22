package com.javarush.island.rukavanov.entity.animals.predators;

import com.javarush.island.rukavanov.config.OrganismType;
import com.javarush.island.rukavanov.entity.animals.Animal;

public abstract class Predator extends Animal {

    protected Predator(OrganismType data) {
        super(data);
    }

}
