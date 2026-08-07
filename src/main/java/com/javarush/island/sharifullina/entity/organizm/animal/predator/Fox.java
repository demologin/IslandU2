package com.javarush.island.sharifullina.entity.organizm.animal.predator;

import com.javarush.island.sharifullina.api.annotation.TypedOrganizm;

@TypedOrganizm(type = "Fox")
public class Fox extends Predator {
    public Fox(double weight) {
        super(weight);
        icon = "\uD83E\uDD8A";
    }
}
