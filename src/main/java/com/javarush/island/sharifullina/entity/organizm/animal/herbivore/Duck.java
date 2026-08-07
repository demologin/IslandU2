package com.javarush.island.sharifullina.entity.organizm.animal.herbivore;

import com.javarush.island.sharifullina.api.annotation.TypedOrganizm;

@TypedOrganizm(type = "Duck")
public class Duck extends Herbivore{
    public Duck(double weight) {
        super(weight);
        icon = "\uD83E\uDD86";
    }
}
