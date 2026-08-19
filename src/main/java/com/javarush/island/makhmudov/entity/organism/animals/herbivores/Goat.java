package com.javarush.island.makhmudov.entity.organism.animals.herbivores;

import com.javarush.island.makhmudov.entity.organism.Limit;
import com.javarush.island.makhmudov.api.annotation.DataType;

@DataType(name = "Коза", icon = "\uD83D\uDC10", maxWeight = 60, maxCountInCell = 140,
        flockSize = 35, maxSpeed = 3, maxFood = 10)
public class Goat extends Herbivore {
    public Goat(String name, String icon, Limit limit) {
        super(name, icon, limit);
    }
}
