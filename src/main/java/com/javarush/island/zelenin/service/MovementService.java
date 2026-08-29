package com.javarush.island.zelenin.service;

import com.javarush.island.zelenin.config.GameConfig;
import com.javarush.island.zelenin.entity.map.Island;
import com.javarush.island.zelenin.entity.map.Location;
import com.javarush.island.zelenin.entity.organism.OrganismType;
import com.javarush.island.zelenin.entity.organism.animal.Animal;
import com.javarush.island.zelenin.repository.GameStats;
import com.javarush.island.zelenin.util.RandomUtils;
import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MovementService {
    private final GameConfig config;
    private final Island island;

    public void prepareReservations(Location location) {
        Map<OrganismType, Long> counts = location.getAnimals().stream()
                .collect(Collectors.groupingBy(Animal::getType, Collectors.counting()));

        location.getReservations().forEach((animalType, counter) -> {
            int count = counts.getOrDefault(animalType, 0L).intValue();
            counter.set(count);
        });
    }

    public void planDepartures(Location location) {
        Iterator<Animal> animals = location.getAnimals().iterator();

        while (animals.hasNext()) {
            Animal animal = animals.next();

            int radius = config.getMaxSpeed(animal.getType());
            List<Location> neighbors = island.getReachableFrom(location, radius);
            Location targetLocation = chooseDirection(location, neighbors);

            if (targetLocation != location) {
                if (tryReservePlace(targetLocation, animal)) {
                    targetLocation.getArrivalQueue().add(animal);
                    animals.remove();
                    GameStats.decrement(animal.getType());
                }
            }
        }
    }

    public Location chooseDirection(Location currentLocation, List<Location> reachableLocations) {
        if (reachableLocations.isEmpty()) return currentLocation;
        int index = RandomUtils.nextInt(0, reachableLocations.size());
        return reachableLocations.get(index);
    }

    private boolean tryReservePlace(Location location, Animal animal) {
        AtomicInteger counter = location.getReservations().get(animal.getType());
        while (true) {
            int count = counter.get();
            if (count >= config.getMaxCount(animal.getType())) return false;
            if (counter.compareAndSet(count, count + 1)) return true;
        }
    }

    public void processArrivals(Location location) {
        while (!location.getArrivalQueue().isEmpty()) {
            Animal newcomer = location.getArrivalQueue().poll();
            if (newcomer != null) {
                location.getAnimals().add(newcomer);
                GameStats.increment(newcomer.getType());
            }
        }
    }
}
