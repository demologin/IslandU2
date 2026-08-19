package com.javarush.island.makhmudov.entity.organism.animals.predators;

import com.javarush.island.makhmudov.entity.organism.Limit;
import com.javarush.island.makhmudov.api.annotation.DataType;

@DataType(name = "Волк", icon = "\uD83D\uDC3A", maxWeight = 50, maxCountInCell = 30,
        flockSize = 4, maxSpeed = 3, maxFood = 8)
public class Wolf extends Predator {

    public Wolf(String name, String icon, Limit limit) {
        super(name, icon, limit);
    }
}
