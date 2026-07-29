package com.javarush.island.zimina.animalcrossing.model.animals.predators;

import com.javarush.island.zimina.animalcrossing.config.AnimalSpeciesConfig;
import com.javarush.island.zimina.animalcrossing.map.Location;
import com.javarush.island.zimina.animalcrossing.model.animals.Animal;
import com.javarush.island.zimina.animalcrossing.model.animals.Carnivorous;

/**
 * Базовый класс для всех хищников на острове.
 * Реализует общий механизм поедания добычи по вероятности:
 * конкретные виды предоставляют свою таблицу вероятностей через getFoodProbabilities().
 */
public abstract class Predator extends Animal implements Carnivorous {

    protected Predator(int x, int y, AnimalSpeciesConfig config) {
        super(x, y, config);
    }

    @Override
    public void eat(Location location) {
        for (Animal animal : location.getAnimals()) {
            if (animal == this || !animal.isAlive()) {
                continue;
            }
            String preyType = animal.getClass().getSimpleName();
            if (tryToEat(preyType)) {
                animal.die(location);
                increaseSatiety(animal.getWeight());
                break;
            }
        }
    }
}