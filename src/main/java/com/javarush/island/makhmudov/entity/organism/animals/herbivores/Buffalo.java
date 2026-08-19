package com.javarush.island.makhmudov.entity.organism.animals.herbivores;

import com.javarush.island.makhmudov.entity.organism.Limit;
import com.javarush.island.makhmudov.api.annotation.DataType;

@DataType(name = "Буйвол", icon = "\uD83D\uDC03", maxWeight = 700, maxCountInCell = 10,
        flockSize = 2, maxSpeed = 3, maxFood = 100)
public class Buffalo extends Herbivore {
    public Buffalo(String name, String icon, Limit limit) {
        super(name, icon, limit);
    }
}
