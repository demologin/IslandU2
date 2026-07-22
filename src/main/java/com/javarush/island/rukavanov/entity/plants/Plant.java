package com.javarush.island.rukavanov.entity.plants;

import com.javarush.island.rukavanov.config.OrganismType;
import com.javarush.island.rukavanov.entity.Organism;

public class Plant extends Organism {

    @Override
    public OrganismType getData() {
        return OrganismType.PLANT;

    }

}
