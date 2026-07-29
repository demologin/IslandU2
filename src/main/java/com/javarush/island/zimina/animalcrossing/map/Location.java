package com.javarush.island.zimina.animalcrossing.map;

import lombok.Getter;
import com.javarush.island.zimina.animalcrossing.model.animals.Animal;
import com.javarush.island.zimina.animalcrossing.model.plant.Plant;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Одна клетка острова.
 * Хранит животных и растения, находящихся на ней,
 * и обеспечивает потокобезопасный доступ к своему содержимому.
 */
@Getter
public class Location {

    /** Животные, находящиеся на этой клетке. */
    private final List<Animal> animals = new CopyOnWriteArrayList<>();

    /** Растения, находящиеся на этой клетке. */
    private final List<Plant> plants = new CopyOnWriteArrayList<>();

    /** Блокировка для потокобезопасного доступа к клетке. */
    private final Lock lock = new ReentrantLock();

    /**
     * Возвращает количество растений на этой клетке.
     *
     * @return количество растений
     */
    public int getPlantsCount() {
        return plants.size();
    }

    /**
     * Считает, сколько животных указанного вида находится на этой клетке.
     *
     * @param type имя вида животного (например, "Rabbit")
     * @return количество особей этого вида на клетке
     */
    public long countAnimalsOfType(String type) {
        return animals.stream()
                .filter(a -> a.getClass().getSimpleName().equals(type))
                .count();
    }
}