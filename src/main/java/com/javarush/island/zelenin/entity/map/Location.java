package com.javarush.island.zelenin.entity.map;

import com.javarush.island.zelenin.entity.organism.OrganismType;
import com.javarush.island.zelenin.entity.organism.animal.Animal;
import com.javarush.island.zelenin.entity.organism.plant.Plant;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Location {
    private final int row;
    private final int col;
    private final List<Animal> animals = new ArrayList<>();
    private final List<Plant> plants = new ArrayList<>();
    private final Map<OrganismType, AtomicInteger> reservations = new HashMap<>(); //TODO better names
    private final ConcurrentLinkedQueue<Animal> arrivalQueue = new ConcurrentLinkedQueue<>();

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
        for (OrganismType type : OrganismType.getAnimals()) {
            reservations.put(type, new AtomicInteger(0));
        }
    }

//    public void add(Organism organism) {
//        switch (organism) {
//            case Animal animal -> animals.add(animal);
//            case Plant plant -> plants.add(plant);
//            default -> throw new GameException("Unexpected value: " + organism); //TODO message
//        }
//    }
}
