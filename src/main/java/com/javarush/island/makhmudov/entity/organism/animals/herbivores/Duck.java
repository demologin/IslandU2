package com.javarush.island.makhmudov.entity.organism.animals.herbivores;

import com.javarush.island.makhmudov.entity.organism.Limit;
import com.javarush.island.makhmudov.api.annotation.DataType;

@DataType(name = "Утка", icon = "\uD83E\uDD86", maxWeight = 1, maxCountInCell = 200,
        flockSize = 50, maxSpeed = 4, maxFood = 0.15)
public class Duck extends Herbivore {
    public Duck(String name, String icon, Limit limit) {
        super(name, icon, limit);
    }
}
