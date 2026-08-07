package com.javarush.island.sharifullina.entity.organizm.animal.herbivore;

import com.javarush.island.sharifullina.api.annotation.TypedOrganizm;

@TypedOrganizm(type = "Buffalo")
public class Buffalo extends Herbivore{
    public Buffalo(double weight) {
        super(weight);
        icon = "\uD83D\uDC03";
    }
}
