package com.javarush.island.zelenin.entity.organism.animal;

import com.javarush.island.zelenin.entity.organism.Organism;
import com.javarush.island.zelenin.entity.organism.OrganismType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Animal extends Organism {
    private double satiety;

    protected Animal(OrganismType type) {
        super(type);
    }

    public void increaseSatiety(double amount) {
        satiety += amount;
    }

    public void decreaseSatiety(double amount) {
        satiety -= amount;
    }
}
