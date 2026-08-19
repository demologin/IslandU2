package com.javarush.island.makhmudov.entity.organism.animals.herbivores;

import com.javarush.island.makhmudov.entity.organism.Limit;
import com.javarush.island.makhmudov.api.annotation.DataType;

@DataType(name = "Мышка", icon = "\uD83D\uDC01", maxWeight = 0.05, maxCountInCell = 500,
        flockSize = 50, maxSpeed = 1, maxFood = 0.01)
public class Mouse extends Herbivore {
    public Mouse(String name, String icon, Limit limit) {
        super(name, icon, limit);
    }

}
