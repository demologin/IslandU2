package com.javarush.island.baksalyar.entity.organizm;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javarush.island.baksalyar.entity.interfaces.Eating;
import com.javarush.island.baksalyar.entity.interfaces.Movement;
import com.javarush.island.baksalyar.entity.interfaces.Reproducible;
import com.javarush.island.baksalyar.entity.map.Cell;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicLong;
/**
 * Abstract base class for all organisms in the simulation.
 * <p>
 * Organisms are the fundamental entities of the game world. Each organism has a
 * unique ID, a type, a name, an icon (emoji), a current weight (energy), and a
 * set of limits (max weight, max speed, max food, etc.). They can move, eat,
 * reproduce (though not all organisms implement reproduction), and interact with
 * their environment.
 * </p>
 * <p>
 * Organisms are designed to be cloned (via {@link #clone()}) to produce new
 * instances with a fresh ID and maximum weight. The cloning mechanism is used
 * during map initialization and reproduction.
 * </p>
 * <p>
 * All methods that modify a {@link Cell} are thread-safe, as they acquire the
 * cell's lock before making changes.
 * </p>
 * <p>
 * This class implements {@link Movement}, {@link Eating}, and {@link Reproducible},
 * requiring subclasses to provide concrete implementations for these behaviors.
 * It also extends {@link Cloneable} to support cloning.
 * </p>
 */
@Getter
@Setter
public abstract class Organism implements Movement, Cloneable, Eating, Reproducible {

    private int power = 0;
    private final Limit limit;
    private String name;
    private static final AtomicLong idCounter = new AtomicLong(System.currentTimeMillis());
    private long id = idCounter.incrementAndGet();
    private String icon;
    private double weight;
    private boolean isParticipates;
    private String type;
    @JsonCreator
    public Organism(String name, Limit limit, String icon) {
        this.limit = limit;
        this.name = name;
        this.icon = icon;
    }
    /**
     * Clones this organism using standard {@code super.clone()}.
     * <p>
     * The cloned instance receives a new unique ID and its weight is set to
     * the maximum weight defined by its limit. This ensures that clones start
     * at full health/energy.
     * </p>
     *
     * @return a shallow copy of this organism with updated ID and weight
     * @throws CloneNotSupportedException if the clone operation fails
     */
    @Override
    protected Organism clone() throws CloneNotSupportedException {
        Organism clone = (Organism) super.clone();
        clone.id = idCounter.incrementAndGet();
        clone.weight = limit.getMaxWeight();
        return clone;
    }
    /**
     * Convenience method to clone any organism without handling checked exceptions.
     * Uses the protected {@link #clone()} method internally.
     */
    public static <T extends Organism> T clone(T original) {
        try {
            return (T) original.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
    /**
     * Safely changes the organism's weight by a percentage of its maximum weight.
     * <p>
     * The method locks the current cell to ensure thread safety, validates that
     * the organism is present in the cell, and adjusts the weight within the range
     * [0, maxWeight]. A positive percentage increases weight, a negative percentage
     * decreases it.
     * </p>
     * <p>
     * This method is typically used to simulate energy loss (e.g., from hunger)
     * or energy gain (e.g., from eating).
     * </p>
     *
     * @param currentCell the cell where the organism currently resides
     * @param percent     the percentage change (e.g., 10 for +10%, -5 for -5%)
     * @return {@code true} if the organism is present in the cell and the weight
     *         was successfully updated, {@code false} otherwise
     */
    protected boolean safeChangeWeight(Cell currentCell, int percent) {
        currentCell.getLock().lock();
        try {
            double maxWeight = limit.getMaxWeight();
            weight += maxWeight * percent / 100;
            weight = Math.max(0, weight);
            weight = Math.min(weight, maxWeight);
            return isHere(currentCell) && currentCell
                    .getResidents()
                    .get(type)
                    .contains(this);
        } finally {
            currentCell.getLock().unlock();
        }
    }
    /**
     * Calculates the amount of food (weight) the organism needs to reach its maximum
     * food capacity, constrained by the maximum weight.
     * <p>
     * This value is used during feeding to determine how much food can be consumed
     * before the organism is full.
     * </p>
     */
    @JsonIgnore
    public double getNeedFood() {
        return Math.min(
                getLimit().getMaxFood(),
                getLimit().getMaxWeight() - getWeight());
    }
    /**
     * Checks whether this organism is present in the given cell by looking up its
     * type in the cell's residents and verifying it contains this instance.
     */
    protected boolean isHere(Cell cell) {
        return cell.getResidents().get(this.getType()).contains(this);
    }
    /**
     * Safely removes this organism from the given target cell.
     * <p>
     * Locks the target cell, verifies that the organism is present, and removes it
     * from the residents map. If successful, the organism is effectively "dead"
     * and should be discarded from the simulation.
     * </p>
     * <p>
     * This method is thread-safe.
     * </p>
     *
     * @param target the cell from which to remove the organism
     * @return {@code true} if the organism was successfully removed,
     *         {@code false} if it was not present
     */
    protected boolean safeDie(Cell target) {
        target.getLock().lock();
        try {
            return isHere(target)
                    && target
                    .getResidents()
                    .get(type)
                    .remove(this);
        } finally {
            target.getLock().unlock();
        }
    }
}
