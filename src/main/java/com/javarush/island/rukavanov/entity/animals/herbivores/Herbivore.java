package com.javarush.island.rukavanov.entity.animals.herbivores;

import com.javarush.island.rukavanov.config.OrganismType;
import com.javarush.island.rukavanov.entity.animals.Animal;

public abstract class Herbivore extends Animal {

    protected Herbivore(OrganismType data) {
        super(data);

    }

}
