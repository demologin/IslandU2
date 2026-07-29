package com.javarush.island.zimina.animalcrossing.model.animals;

import lombok.Getter;
import com.javarush.island.zimina.animalcrossing.config.AnimalSpeciesConfig;
import com.javarush.island.zimina.animalcrossing.config.SimulationConfig;
import com.javarush.island.zimina.animalcrossing.map.Island;
import com.javarush.island.zimina.animalcrossing.map.Location;
import com.javarush.island.zimina.animalcrossing.model.Organism;
import com.javarush.island.zimina.animalcrossing.simulation.AnimalFactory;
import com.javarush.island.zimina.animalcrossing.util.ProbabilityUtils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Базовый класс для всех животных на острове.
 * Содержит общее поведение и характеристики: скорость передвижения,
 * потребность в пище и текущий уровень сытости.
 */
@Getter
public abstract class Animal extends Organism {

    private static final int REPRODUCTION_CHANCE = 45;
    private static final double HUNGER_RATE = 2;

    private final int speed;

    private final double foodNeeded;

    private double satiety;

    protected Animal(int x, int y, AnimalSpeciesConfig config) {
        super(x, y, config.getWeight());
        this.speed = config.getSpeed();
        this.foodNeeded = config.getFoodNeeded();
        this.satiety = config.getFoodNeeded();
    }

    public abstract void eat(Location location);

    public void move(Island island, Location location) {
        int deltaX = ThreadLocalRandom.current().nextInt(-speed, speed + 1);
        int deltaY = ThreadLocalRandom.current().nextInt(-speed, speed + 1);

        int newX = Math.clamp(getX() + deltaX, 0, SimulationConfig.ISLAND_WIDTH - 1);
        int newY = Math.clamp(getY() + deltaY, 0, SimulationConfig.ISLAND_HEIGHT - 1);

        location.getLock().lock();
        try {
            location.getAnimals().remove(this);
        } finally {
            location.getLock().unlock();
        }

        setX(newX);
        setY(newY);

        Location newLocation = island.getLocation(newX, newY);
        newLocation.getLock().lock();
        try {
            newLocation.getAnimals().add(this);
        } finally {
            newLocation.getLock().unlock();
        }
    }

    public void reproduce(Location location, AnimalFactory factory, AnimalSpeciesConfig config) {
        String type = getClass().getSimpleName();
        long currentCount = location.countAnimalsOfType(type);

        if (currentCount >= 2
                && currentCount < config.getMaxPerCell()
                && ProbabilityUtils.rollChance(REPRODUCTION_CHANCE)) {
            Animal offspring = factory.createAnimal(type, getX(), getY(), config);
            if (offspring != null) {
                location.getLock().lock();
                try {
                    location.getAnimals().add(offspring);
                } finally {
                    location.getLock().unlock();
                }
            }
        }
    }
    /**
     * Отмечает животное как погибшее и убирает его с клетки.
     *
     * @param location клетка, на которой находится животное
     */
    public void die(Location location) {
        die();
        location.getLock().lock();
        try {
            location.getAnimals().remove(this);
        } finally {
            location.getLock().unlock();
        }
    }

    /**
     * Увеличивает сытость животного на указанное количество килограммов,
     * но не выше значения, необходимого для полного насыщения.
     *
     * @param amount количество съеденной пищи в килограммах
     */
    public void increaseSatiety(double amount) {
        satiety = Math.min(satiety + amount, foodNeeded);
    }

    /**
     * Уменьшает сытость животного за такт симуляции в зависимости от его скорости.
     * Если сытость опускается до нуля или ниже — животное умирает и убирается с клетки.
     *
     * @param location клетка, на которой находится животное
     */
    public void starve(Location location) {
        double loss = foodNeeded * speed * HUNGER_RATE / 100;
        satiety -= loss;
        if (satiety <= 0) {
            satiety = 0;
            die(location);
        }
    }
}