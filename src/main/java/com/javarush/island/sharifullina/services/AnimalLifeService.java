package com.javarush.island.sharifullina.services;

import com.javarush.island.sharifullina.entity.organizm.TypePopulation;
import com.javarush.island.sharifullina.entity.map.Cell;
import com.javarush.island.sharifullina.entity.organizm.animal.Animal;

import java.util.Map;

public class AnimalLifeService extends LifeService {

    public AnimalLifeService(Map<Cell, TypePopulation> organizms) {
        super(organizms);
    }

    @Override
    public void run() {
        organizms.entrySet().stream().forEach(
                entry -> {
                    entry.getValue()
                            .getOrganizms()
                            .stream()
                            .map(organizm -> (Animal) organizm)
                            .forEach(animal -> {
                                Cell cell = entry.getKey();
                                if (animal.eat(cell)) {
                                    animal.safeReproduce(cell);
                                }
                                animal.loseWeight(cell);
                                animal.move(cell);
                            });
                });
    }
}























