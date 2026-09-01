package com.javarush.island.zelenin.entity.organism.animal.herbivore;

import com.javarush.island.zelenin.entity.organism.OrganismType;
import com.javarush.island.zelenin.entity.organism.animal.Animal;

public abstract class Herbivore extends Animal {

    protected Herbivore(OrganismType type) {
        super(type);
    }
}
