package com.javarush.island.makhmudov.entity.organism.animals.predators;

import com.javarush.island.makhmudov.entity.organism.Limit;
import com.javarush.island.makhmudov.api.annotation.DataType;

@DataType(name = "Кабан", icon = "\uD83D\uDC17", maxWeight = 400, maxCountInCell = 50,
        flockSize = 10, maxSpeed = 2, maxFood = 50)
public class Boar extends Predator {

    public Boar(String name, String icon, Limit limit) {
        super(name, icon, limit);
    }
}
