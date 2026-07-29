package com.javarush.island.zimina.animalcrossing.map;

import lombok.Getter;
import com.javarush.island.zimina.animalcrossing.model.animals.Animal;
import com.javarush.island.zimina.animalcrossing.model.plant.Plant;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Getter
public class Location {

    private final List<Animal> animals = new CopyOnWriteArrayList<>();
    private final List<Plant> plants = new CopyOnWriteArrayList<>();
    private final Lock lock = new ReentrantLock();

    public int getPlantsCount() {
        return plants.size();
    }

    public long countAnimalsOfType(String type) {
        return animals.stream()
                .filter(a -> a.getClass().getSimpleName().equals(type))
                .count();
    }
}