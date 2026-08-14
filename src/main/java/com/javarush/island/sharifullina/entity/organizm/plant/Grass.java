package com.javarush.island.sharifullina.entity.organizm.plant;

import com.javarush.island.sharifullina.api.annotation.TypedOrganizm;

@TypedOrganizm(type = "Grass")
public class Grass extends Plant{
    public Grass(double weight) {
        super(weight);
        icon ="🌿";
    }
}
