package com.javarush.island.rukavanov.factory;

import com.javarush.island.khmelov.entity.organizm.animals.predators.*;
import com.javarush.island.rukavanov.config.AnimalData;
import com.javarush.island.rukavanov.entity.Organism;

public final class OrganismFactory {

    private OrganismFactory() {

    }

    public static Organism create(AnimalData data) {

        return switch (data) {

            case WOLF -> new Wolf();
            case SNAKE -> new Snake();
            case FOX -> new Fox();
            case BEAR -> new Bear();
            case EAGLE -> new Eagle();
            case HORSE -> new Horse();
            case DEER -> new Deer();
            case RABBIT -> new Rabbit();
            case MOUSE -> new Mouse();
            case GOAT -> new Goat();
            case SHEEP -> new Sheep();
            case BOAR -> new Boar();
            case BUFFALO -> new Buffalo();
            case DUCK -> new Duck();
            case CATERPILLAR -> new Caterpillar();
            case PLANT -> new Plant();

        };

    }

}
