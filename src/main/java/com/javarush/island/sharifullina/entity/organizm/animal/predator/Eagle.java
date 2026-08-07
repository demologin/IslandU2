package com.javarush.island.sharifullina.entity.organizm.animal.predator;

import com.javarush.island.sharifullina.api.annotation.TypedOrganizm;

@TypedOrganizm(type = "Eagle")
public class Eagle extends Predator {
    public Eagle(double weight) {
        super(weight);
        icon = "\uD83E\uDD85";
    }
}
