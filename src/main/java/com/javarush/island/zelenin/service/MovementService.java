package com.javarush.island.zelenin.service;

import com.javarush.island.zelenin.config.GameConfig;
import com.javarush.island.zelenin.entity.map.Island;
import com.javarush.island.zelenin.entity.map.Location;
import com.javarush.island.zelenin.entity.organism.OrganismType;
import com.javarush.island.zelenin.entity.organism.animal.Animal;
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

    /**
     * Syncs animal "reservations" with the actual number of animal packs currently residing
     * in a given {@link Location}. Reservations store the number of animals currently living
     * in the location plus any number of animals that are allowed to move to that location
     * in the following movement cycle.
     * @param location the location for which to evaluate its reservations
     */
    public void prepareReservations(Location location) {
        Map<OrganismType, Long> packCounts = location.getAnimals().stream()
                .collect(Collectors.groupingBy(Animal::getType, Collectors.counting()));

        location.getReservations().forEach((animalType, counter) ->
                counter.set(packCounts.getOrDefault(animalType, 0L).intValue()));
    }

    /**
     * Moves animal from one {@link Location} into another's location staging queue if there's
     * available space in that location. The target location is chosen randomly among the locations
     * that are within a certain radius of the original location.
     * @param location the original location
     */
    public void planDepartures(Location location) {
        Iterator<Animal> animals = location.getAnimals().iterator();

        while (animals.hasNext()) {
            Animal animal = animals.next();
            int radius = config.getMaxSpeed(animal);
            List<Location> neighbors = island.getReachableFrom(location, radius);

            int index = RandomUtils.nextInt(0, neighbors.size());
            Location targetLocation = neighbors.get(index);
            if (targetLocation == location) continue;

            if (tryReservePlace(targetLocation, animal)) {
                targetLocation.getArrivalQueue().add(animal);
                animals.remove();
            }
        }
    }

    private boolean tryReservePlace(Location location, Animal animal) {
        AtomicInteger counter = location.getReservations().get(animal.getType());
        int maxPacks = config.getMaxCount(animal) / config.getPackSize(animal);

        while (true) {
            int count = counter.get();
            if (count >= maxPacks) return false;
            if (counter.compareAndSet(count, count + 1)) return true;
        }
    }

    /**
     * Puts incoming animals from a {@link Location}'s staging arrival queue into
     * the actual list of animals that currently reside in that location.
     * @param location the processed location
     */
    public void processArrivals(Location location) {
        while (!location.getArrivalQueue().isEmpty()) {
            Animal newcomer = location.getArrivalQueue().poll();
            if (newcomer != null) {
                location.getAnimals().add(newcomer);
            }
        }
    }
}
