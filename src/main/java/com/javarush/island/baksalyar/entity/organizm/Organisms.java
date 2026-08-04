package com.javarush.island.baksalyar.entity.organizm;

import com.javarush.island.baksalyar.entity.map.Residents;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;
/**
 * A wrapper around a set of organisms of the same type, residing in a single cell.
 * <p>
 * Provides methods for adding, removing, and querying organisms, as well as
 * calculating the "effective" population size considering flocking behavior.
 * The icon and limit are lazily cached from the first organism in the set.
 * </p>
 * <p>
 * This class is used by {@link Residents} to store all organisms of a specific type
 * within a cell. It uses a {@link LinkedHashSet} to preserve insertion order.
 * </p>
 */
public class Organisms {
    private final Set<Organism> organisms = new LinkedHashSet<>();
    private Limit limit;
    private String icon = "?";
    private String letter = "?";
    /**
     * Returns the number of organisms physically present in this collection.
     *
     * @return the actual size of the set
     */
    public int size() {return organisms.size();}
    /**
     * Calculates the effective population size, taking into account flocking behavior.
     * <p>
     * If the flock size ({@link Limit#getFlockSize()}) is greater than 1,
     * the method computes a weighted value based on the total weight of all organisms
     * divided by the maximum weight, then multiplied by the flock size.
     * This effectively treats a group of animals as a larger "population unit".
     * Otherwise, the actual number of organisms is returned.
     * </p>
     *
     * @return the effective count (could be a fractional value)
     */
    public double calculateSize() {
        int size = organisms.size();
        if (size > 0 && getLimit().getFlockSize() > 1) {
            double fullWeight = organisms
                    .stream()
                    .mapToDouble(Organism::getWeight)
                    .sum();
            Limit limit = getLimit();
            int flockSize = limit.getFlockSize();
            double part = fullWeight / limit.getMaxWeight();
            return part * flockSize;
        }
        return size;
    }
    /**
     * Returns the cached icon of the organism type.
     * Lazily updates the cache from the first organism if needed.
     *
     * @return the icon string
     */
    public String getIcon() {
        update();
        return icon;
    }
    /**
     * Returns the cached limit object of the organism type.
     * Lazily updates the cache from the first organism if needed.
     *
     * @return the Limit object
     */
    public Limit getLimit() {
        update();
        return limit;
    }
    /**
     * Updates the cached limit and icon from the first organism in the set,
     * but only if the cache is currently uninitialized and the set is not empty.
     */
    private void update() {
        if (limit == null) {
            if (!organisms.isEmpty()) {
                Organism organism = organisms
                        .iterator()
                        .next();
                limit = organism.getLimit();
                icon = organism.getIcon();
            }
        }
    }
    /**
     * Adds all organisms from the given set to this collection.
     *
     * @param newOrganisms a set of organisms to add
     */
    public void addAll(Set<Organism> newOrganisms) {
        organisms.addAll(newOrganisms);
    }
    /**
     * Adds a single organism to the collection.
     *
     * @param organism the organism to add
     * @return {@code true} if the organism was added successfully
     */
    public boolean add(Organism organism) {
        return organisms.add(organism);
    }
    /**
     * Checks if the given organism is present in this collection.
     *
     * @param organism the organism to check
     * @return {@code true} if present, {@code false} otherwise
     */
    public boolean contains(Organism organism) {
        return organisms.contains(organism);
    }
    /**
     * Removes the given organism from the collection.
     *
     * @param organism the organism to remove
     * @return {@code true} if the organism was present and removed
     */
    public boolean remove(Organism organism) {
        return organisms.remove(organism);
    }
    /**
     * Performs the given action on each organism in the collection.
     *
     * @param action the action to apply to each organism
     */
    public void forEach(Consumer<? super Organism> action) {organisms.forEach(action);}
    /**
     * Returns a sequential stream of the organisms in this collection.
     *
     * @return a stream of organisms
     */
    public Stream<Organism> stream() {
        return organisms.stream();
    }
    /**
     * Returns an iterator over the organisms in this collection.
     *
     * @return an iterator
     */
    public Iterator<Organism> iterator() {return organisms.iterator();}
    /**
     * Checks whether this collection is empty.
     *
     * @return {@code true} if no organisms are present, {@code false} otherwise
     */
    public boolean isEmpty() {
        return organisms.isEmpty();
    }
}
