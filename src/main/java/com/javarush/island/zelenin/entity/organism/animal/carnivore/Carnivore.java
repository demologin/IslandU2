package com.javarush.island.zelenin.entity.organism.animal.carnivore;

import com.javarush.island.zelenin.entity.organism.OrganismType;
import com.javarush.island.zelenin.entity.organism.animal.Animal;

public abstract class Carnivore extends Animal {

    protected Carnivore(OrganismType type) {
        super(type);
    }
}
