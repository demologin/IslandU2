package com.javarush.island.rukavanov.entity.animals;

import com.javarush.island.rukavanov.config.FoodMatrix;
import com.javarush.island.rukavanov.config.OrganismType;
import com.javarush.island.rukavanov.config.Settings;
import com.javarush.island.rukavanov.entity.Organism;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Animal extends Organism {


    private double satiety;
    private boolean alive;

    protected Animal(OrganismType type) {

        super(type);
        this.satiety = type.getFoodNorm();
        this.alive = true;

    }


    public boolean isAlive() {
        return alive;
    }


    public void die() {
        alive = false;
    }


    public boolean isHungry() {
        return satiety < getData().getFoodNorm();
    }


    public boolean isFull() {

        return satiety >= getData().getFoodNorm();

    }


//    public void loseSatiety(double amount) {
//
//        if (!alive) {
//            return;
//        }
//
//        satiety -= amount;
//
//        if (satiety <= 0) {
//            satiety = 0;
//            die();
//        }
//    }

    public void eat(Organism food) {

        if (!alive) {
            return;
        }

        satiety += food.getWeight();

        if (satiety > getData().getFoodNorm()) {
            satiety = getData().getFoodNorm();
        }
    }

    public void resetSatiety() {
        satiety = getData().getFoodNorm();
    }


    public double getSatiety() {
        return satiety;
    }


    public double getSatietyPercent() {
        return satiety / getData().getFoodNorm();
    }

    public int getChanceToEat(OrganismType foodType) {
        return FoodMatrix.getChance(getData(), foodType);
    }

    public void loseSatiety() {

        loseSatiety(
                getData().getFoodNorm()
                        * Settings.HUNGER_LOSS_PERCENT
        );

    }
}