package com.javarush.island.zimina.animalcrossing.model.animals.omnivores;

import com.javarush.island.zimina.animalcrossing.config.AnimalSpeciesConfig;
import com.javarush.island.zimina.animalcrossing.map.Location;
import com.javarush.island.zimina.animalcrossing.model.animals.Animal;
import com.javarush.island.zimina.animalcrossing.model.animals.Carnivorous;
import com.javarush.island.zimina.animalcrossing.model.plant.Plant;
import com.javarush.island.zimina.animalcrossing.util.ProbabilityUtils;

public abstract class Omnivore extends Animal implements Carnivorous {

    private static final int PLANT_EATING_CHANCE = 100;

    protected Omnivore(int x, int y, AnimalSpeciesConfig config) {
        super(x, y, config);
    }

    @Override
    public void eat(Location location) {
        boolean atePlant = eatPlant(location);
        if (!atePlant) {
            eatAnimal(location);
        }
    }

    private boolean eatPlant(Location location) {
        if (!location.getPlants().isEmpty()
                && ProbabilityUtils.rollChance(PLANT_EATING_CHANCE)) {
                Plant plant = location.getPlants().getFirst();
                double amountToEat = Math.min(getFoodNeeded() - getSatiety(), plant.getWeight());
                plant.reduceWeight(amountToEat);
                increaseSatiety(amountToEat);
                if (plant.getWeight() == 0) {
                    location.getPlants().remove(plant);
                }
                return true;
            }

        return false;
    }

    private void eatAnimal(Location location) {
        for (Animal animal : location.getAnimals()) {
            if (animal == this || !animal.isAlive()) {
                continue;
            }
            String preyType = animal.getClass().getSimpleName();
            if (tryToEat(preyType)) {
                animal.die(location);
                location.getAnimals().remove(animal);
                increaseSatiety(animal.getWeight());
                break;
            }
        }
    }
}