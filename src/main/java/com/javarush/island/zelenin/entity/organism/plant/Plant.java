package com.javarush.island.zelenin.entity.organism.plant;

import com.javarush.island.zelenin.entity.organism.Organism;
import com.javarush.island.zelenin.entity.organism.OrganismType;

public abstract class Plant extends Organism {

    protected Plant(OrganismType type) {
        super(type);
    }
}
