package com.javarush.island.rukavanov.entity.animals;

import com.javarush.island.rukavanov.config.AnimalData;
import com.javarush.island.rukavanov.entity.Organism;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Animal extends Organism {


    private final AnimalData data;
    private double satiety;
    private boolean alive = true;
    private boolean moved;

    protected Animal(AnimalData data) {

        this.data = data;
        this.satiety = data.getFoodNorm();

    }

    @Override
    public AnimalData getData() {
        return data;
    }

    public void setSatiety(double satiety) {
        this.satiety = Math.max(0, satiety);

    }

    public void eat(double foodWeight) {
        satiety = Math.min(
                satiety + foodWeight,
                data.getFoodNorm()

        );

    }

    public void spendEnergy(double value) {

        satiety -= value;
        if (satiety <= 0) {
            alive = false;
            satiety = 0;
        }

    }

    public void die() {
        alive = false;
    }

    public void resetTick() {
        moved = false;
    }

}