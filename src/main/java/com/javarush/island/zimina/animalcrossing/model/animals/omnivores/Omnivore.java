package com.javarush.island.zimina.animalcrossing.model.animals.omnivores;

import com.javarush.island.zimina.animalcrossing.config.AnimalSpeciesConfig;
import com.javarush.island.zimina.animalcrossing.map.Location;
import com.javarush.island.zimina.animalcrossing.model.animals.Animal;
import com.javarush.island.zimina.animalcrossing.model.animals.Carnivorous;
import com.javarush.island.zimina.animalcrossing.model.plant.Plant;
import com.javarush.island.zimina.animalcrossing.util.ProbabilityUtils;

/**
 * Базовый класс для всех всеядных животных на острове.
 * Способен есть как растения, так и других животных по вероятности —
 * сочетает поведение Herbivore и Predator.
 */
public abstract class Omnivore extends Animal implements Carnivorous {

    /** Вероятность съесть растение на клетке (в процентах). */
    private static final int PLANT_EATING_CHANCE = 100;

    /**
     * Создаёт всеядное животное с характеристиками из конфигурации вида.
     *
     * @param x      координата x на острове
     * @param y      координата y на острове
     * @param config конфигурация вида (вес, скорость, потребность в еде)
     */
    protected Omnivore(int x, int y, AnimalSpeciesConfig config) {
        super(x, y, config);
    }

    /**
     * Попытка поесть на текущей клетке — сначала растение, затем животное.
     * Всеядное животное может съесть и то, и другое за один вызов метода.
     *
     * @param location клетка, на которой находится животное
     */
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

    /**
     * Попытка съесть добычу-животное на текущей клетке.
     *
     * @param location клетка, на которой находится животное
     */
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