package com.javarush.island.makhmudov.entity.organism.animals.herbivores;

import com.javarush.island.makhmudov.api.annotation.DataType;
import com.javarush.island.makhmudov.entity.organism.Limit;

@DataType(name = "Кролик", icon = "\uD83D\uDC07", maxWeight = 2, maxCountInCell = 150,
        flockSize = 50, maxSpeed = 2, maxFood = 0.45)
public class Rabbit extends Herbivore {
    public Rabbit(String name, String icon, Limit limit) {
        super(name, icon, limit);
    }
}
