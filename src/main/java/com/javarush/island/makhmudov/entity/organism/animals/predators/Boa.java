package com.javarush.island.makhmudov.entity.organism.animals.predators;

import com.javarush.island.makhmudov.api.annotation.DataType;
import com.javarush.island.makhmudov.entity.organism.Limit;

@DataType(name = "Удав", icon = "\uD83D\uDC0D", maxWeight = 15, maxCountInCell = 30,
        flockSize = 6, maxSpeed = 1, maxFood = 3)
public class Boa extends Predator {

    public Boa(String name, String icon, Limit limit) {
        super(name, icon, limit);
    }

}
