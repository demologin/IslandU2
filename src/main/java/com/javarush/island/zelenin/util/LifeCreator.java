package com.javarush.island.zelenin.util;

import com.javarush.island.khmelov.exception.GameException;
import com.javarush.island.zelenin.config.GameConfig;
import com.javarush.island.zelenin.entity.organism.Organism;
import com.javarush.island.zelenin.entity.organism.OrganismType;
import com.javarush.island.zelenin.entity.organism.animal.Animal;
import com.javarush.island.zelenin.entity.organism.animal.carnivore.*;
import com.javarush.island.zelenin.entity.organism.animal.herbivore.*;
import com.javarush.island.zelenin.entity.organism.plant.Grass;
import com.javarush.island.zelenin.entity.organism.plant.Plant;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LifeCreator {
    private final GameConfig config;

    public Organism createFrom(OrganismType type) {
        if (type.isAnimal()) return createAnimal(type);
        if (type.isPlant()) return createPlant(type);
        throw new GameException(Const.UNKNOWN_TYPE);
    }

    public Animal createAnimal(OrganismType type) {
        Animal animal =  switch (type) {
            case WOLF    -> new Wolf(type);
            case SNAKE   -> new Snake(type);
            case FOX     -> new Fox(type);
            case BEAR    -> new Bear(type);
            case EAGLE   -> new Eagle(type);
            case HORSE   -> new Horse(type);
            case DEER    -> new Deer(type);
            case RABBIT  -> new Rabbit(type);
            case MOUSE   -> new Mouse(type);
            case GOAT    -> new Goat(type);
            case SHEEP   -> new Sheep(type);
            case BOAR    -> new Boar(type);
            case BUFFALO -> new Buffalo(type);
            case DUCK    -> new Duck(type);
            case WORM    -> new Worm(type);
            default -> throw new GameException(Const.UNKNOWN_TYPE);
        };
        animal.setSatiety(config.getMaxSatiety(type));
        return animal;
    }

    public Plant createPlant(OrganismType type) {
        return switch (type) {
            case GRASS -> new Grass(type);
            default -> throw new GameException(Const.UNKNOWN_TYPE);
        };
    }
}
