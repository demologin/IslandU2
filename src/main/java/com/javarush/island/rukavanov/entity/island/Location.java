package com.javarush.island.rukavanov.entity.island;

import com.javarush.island.rukavanov.config.OrganismType;
import com.javarush.island.rukavanov.entity.Organism;
import lombok.Getter;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Getter
public class Location {

    private final int x;
    private final int y;
    private final EnumMap<OrganismType, List<Organism>> organisms = new EnumMap<>(OrganismType.class);

    private final ReentrantLock lock = new ReentrantLock();

    public Location(int x, int y) {

        this.x = x;
        this.y = y;

        for (OrganismType type : OrganismType.values()) {

            organisms.put(type, new ArrayList<>());

        }

    }


    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    public void addOrganism(Organism organism) {
        organisms.get(organism.getData()).add(organism);
    }

    public void removeOrganism(Organism organism) {
        organisms.get(organism.getData()).remove(organism);
    }

    public List<Organism> getOrganisms(OrganismType type) {
        return organisms.get(type);
    }

    public int count(OrganismType type) {
        return organisms.get(type).size();
    }

    public boolean isEmpty(OrganismType type) {
        return organisms.get(type).isEmpty();
    }

    public boolean isFull(OrganismType type) {
        return count(type) >= type.getMaxCount();
    }

    public EnumMap<OrganismType, List<Organism>> getAllOrganisms() {
        return new EnumMap<>(organisms);
    }

}