package com.javarush.island.rukavanov.entity.island;

import com.javarush.island.rukavanov.config.OrganismType;
import com.javarush.island.rukavanov.entity.Organism;
import com.javarush.island.rukavanov.entity.animals.Animal;
import com.javarush.island.rukavanov.util.RandomUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Getter
public class Location {

    private final int x;
    private final int y;
    private final int id;
    private final EnumMap<OrganismType, List<Organism>> organisms = new EnumMap<>(OrganismType.class);

    private final ReentrantLock lock = new ReentrantLock();

    public Location(int x, int y, int id) {

        this.x = x;
        this.y = y;
        this.id = id;

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

    public int count(OrganismType type) {
        return organisms.get(type).size();
    }

    public boolean isEmpty(OrganismType type) {
        return organisms.get(type).isEmpty();
    }

    public boolean isFull(OrganismType type) {
        return count(type) >= type.getMaxCount();
    }

    public Organism getRandomOrganism(OrganismType type) {

        List<Organism> list = organisms.get(type);

        if (list.isEmpty()) {
            return null;
        }

        return list.get(RandomUtil.random(list.size()));

    }


    public boolean contains(Organism organism) {

        return organisms
                .get(organism.getData())
                .contains(organism);

    }

    public List<Animal> getAnimalsSnapshot(OrganismType type) {

        List<Animal> snapshot = new ArrayList<>();

        for (Organism organism : organisms.get(type)) {
            snapshot.add((Animal) organism);
        }

        return snapshot;

    }

    public boolean moveOrganismTo(Location target,
                                  Animal animal) {

        Location first =
                this.id < target.id
                        ? this
                        : target;

        Location second =
                this.id < target.id
                        ? target
                        : this;

        first.lock();
        second.lock();

        try {

            if (!contains(animal)) {
                return false;
            }

            if (target.isFull(animal.getData())) {
                return false;
            }

            removeOrganism(animal);

            target.addOrganism(animal);

            return true;

        } finally {

            second.unlock();
            first.unlock();

        }

    }

    public List<Animal> getAllAnimalsSnapshot() {

        List<Animal> animals = new ArrayList<>();

        for (OrganismType type : OrganismType.animals()) {

            animals.addAll(getAnimalsSnapshot(type));

        }

        return animals;

    }

}