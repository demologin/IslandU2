package com.javarush.island.sharifullina.entity.map;

import com.javarush.island.sharifullina.entity.Population;
import com.javarush.island.sharifullina.entity.TypePopulation;
import com.javarush.island.sharifullina.entity.organizm.*;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Cell {
    private int x;
    private int y;
    @Getter
    private List<Cell> neighbors = new ArrayList<>();
    private Island island;
    @Getter
    private ReentrantLock lock = new ReentrantLock();

    @Getter
    private Population population;

    public Cell(int x, int y, Island island) {
        this.x = x;
        this.y = y;
        this.island = island;
        population = new Population();
    }

    public boolean safeAddOrganizm(Organizm organizm) {
        lock.lock();
        try {
            return population.addOrganizm(organizm);
        } finally {
            lock.unlock();
        }
    }

    public boolean safeRemoveOrganizm(Organizm organizm) {
        lock.lock();
        try {
            return population.removeOrganizm(organizm);
        } finally {
            lock.unlock();
        }
    }

    public Cell getRandomNeighbor() {
        int n = ThreadLocalRandom.current().nextInt(0, neighbors.size());
        return neighbors.get(n);
    }

    public TypePopulation getPopulation(String type) {
        return population.getPopulationMap().get(type);
    }

    public Map<String, Integer> getTopInPercent() {
        return population.getTopStatInPercentMap();
    }

    public Map<String, Integer> getCountMap() {
        return population.getCountOfOrganizmsMap();
    }

    public void initNeighbors() {
        if (x > 0) {
            neighbors.add(island.getCell(x - 1, y));
        }
        if (y > 0) {
            neighbors.add(island.getCell(x, y - 1));
        }
        if (x < island.getRow() - 1) {
            neighbors.add(island.getCell(x + 1, y));
        }
        if (y < island.getColumn() - 1) {
            neighbors.add(island.getCell(x, y + 1));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        population.getPopulationMap()
                .forEach(
                        (type, typePopulation) ->
                                sb.append(" ".repeat(3)
                                                + type.substring(0, 2)
                                                + ": "
                                                + typePopulation.getCount())
                                        .append(" ".repeat(3))
                );
        return "Cell[" + x + "," + y + "] " +
                "" + sb;
    }
}
