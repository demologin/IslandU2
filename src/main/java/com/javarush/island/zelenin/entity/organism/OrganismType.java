package com.javarush.island.zelenin.entity.organism;

import java.util.List;

public enum OrganismType {
    WOLF, SNAKE, FOX, BEAR, EAGLE,
    HORSE, DEER, RABBIT, MOUSE, GOAT, SHEEP, BOAR, BUFFALO, DUCK, WORM,
    TREE, BUSH, GRASS;

    public static List<OrganismType> getAnimals() {
        return List.of(WOLF, SNAKE, FOX, BEAR, EAGLE, HORSE, DEER,
                RABBIT, MOUSE, GOAT, SHEEP, BOAR, BUFFALO, DUCK, WORM);
    }

    public static List<OrganismType> getCarnivores() {
        return List.of(WOLF, SNAKE, FOX, BEAR, EAGLE);
    }

    public static List<OrganismType> getHerbivores() {
        return List.of(HORSE, DEER, RABBIT, MOUSE, GOAT, SHEEP, BOAR, BUFFALO, DUCK, WORM);
    }

    public static List<OrganismType> getPlants() {
        return List.of(TREE, BUSH, GRASS);
    }

    public boolean isAnimal() {
        return getAnimals().contains(this);
    }

    public boolean isCarnivore() {
        return getCarnivores().contains(this);
    }

    public boolean isHerbivore() {
        return getHerbivores().contains(this);
    }

    public boolean isPlant() {
        return getPlants().contains(this);
    }
}
