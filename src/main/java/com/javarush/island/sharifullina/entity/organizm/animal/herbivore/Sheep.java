package com.javarush.island.sharifullina.entity.organizm.animal.herbivore;

import com.javarush.island.sharifullina.api.annotation.TypedOrganizm;
import com.javarush.island.sharifullina.entity.map.Cell;

@TypedOrganizm(type = "Sheep")
public class Sheep extends Herbivore{
    public Sheep(double weight) {
        super(weight);
        icon = "\uD83D\uDC11";
    }
}
