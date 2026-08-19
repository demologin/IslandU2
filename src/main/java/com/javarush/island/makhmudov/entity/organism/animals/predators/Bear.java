package com.javarush.island.makhmudov.entity.organism.animals.predators;

import com.javarush.island.makhmudov.api.annotation.DataType;
import com.javarush.island.makhmudov.entity.organism.Limit;

@DataType(name = "Медведь", icon = "\uD83D\uDC3B", maxWeight = 500, maxCountInCell = 5,
        maxSpeed = 2, maxFood = 80)
public class Bear extends Predator {

    public Bear(String name, String icon, Limit limit) {
        super(name, icon, limit);
    }
}
