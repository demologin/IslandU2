package com.javarush.island.rukavanov.entity.plants;

import com.javarush.island.rukavanov.config.AnimalData;
import com.javarush.island.rukavanov.entity.Organism;

public class Plant extends Organism {

    @Override
    public AnimalData getData() {
        return AnimalData.PLANT;

    }

}
