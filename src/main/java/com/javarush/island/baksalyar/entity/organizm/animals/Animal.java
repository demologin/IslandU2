package com.javarush.island.baksalyar.entity.organizm.animals;

import com.javarush.island.baksalyar.entity.interfaces.Eating;
import com.javarush.island.baksalyar.entity.interfaces.Movement;
import com.javarush.island.baksalyar.entity.map.Cell;
import com.javarush.island.baksalyar.entity.map.Residents;
import com.javarush.island.baksalyar.entity.organizm.Limit;
import com.javarush.island.baksalyar.entity.organizm.Organism;
import com.javarush.island.baksalyar.entity.organizm.Organisms;
import com.javarush.island.baksalyar.util.Rnd;
import lombok.Getter;
import lombok.Setter;
/**
 * Abstract base class for all animals in the simulation.
 * <p>
 * Animals are mobile organisms that can move, eat, and lose weight when hungry.
 * This class implements the {@link Movement} and {@link Eating} interfaces,
 * providing common behavior for both herbivores and predators.
 * </p>
 * <p>
 * Movement is performed by selecting a random neighbor cell up to the animal's
 * maximum speed (number of steps). Eating attempts to find food in the current
 * cell; if unsuccessful, the animal loses 4% of its maximum weight. If weight
 * drops to zero, the animal dies.
 * </p>
 * <p>
 * All methods that modify cell residents are thread‑safe, using the cell's
 * lock for synchronization.
 * </p>
 */
@Getter
@Setter
public abstract class Animal extends Organism implements Movement, Eating {
    /**
     * Attempts to feed the animal.
     * <p>
     * First, tries to find food in the current cell via {@link #safeFindFood(Cell)}.
     * If food is found, returns {@code true}. Otherwise, the animal loses 4% of its
     * maximum weight. If weight becomes zero or less, the animal dies.
     * </p>
     * <p>
     * The return value indicates success in finding food; weight loss or death
     * are side effects.
     * </p>
     *
     * @param currentCell the cell where the animal tries to eat
     * @return {@code true} if food was consumed, {@code false} otherwise
     */
    @Override
    public boolean eat(Cell currentCell) {
        var dd = safeFindFood(currentCell);
        if (dd) {
            return true;
        }
        if (getWeight() > 0) {
            return safeChangeWeight(currentCell, -1 * 4 );
        }

        return !safeDie(currentCell);
    }


    public Animal(String name, Limit limit, String icon) {
        super(name, limit, icon);
    }
    /**
     * Moves the animal to a random neighboring cell.
     * <p>
     * The animal performs up to {@code maxSpeed} steps, each time choosing
     * a random neighbor from the current cell's {@code nextCell} list.
     * The final destination cell is determined, and the animal is moved there.
     * </p>
     *
     * @param startCell the cell where the animal starts
     * @return {@code true} if the move was successful, {@code false} otherwise
     */
    @Override
    public boolean move(Cell startCell) {
        int maxStep = getLimit().getMaxSpeed();
        Cell destinationCell = startCell;
        for (int i = 0; i < maxStep; i++) {
            int indexMax = startCell.getNextCell().size();
            destinationCell = startCell.getNextCell().get(Rnd.random(0,indexMax));
        }
        return safeMove(startCell, destinationCell);
    }
    /**
     * Safely moves the animal from the source cell to the destination cell.
     * <p>
     * Attempts to add the animal to the destination cell; if successful,
     * removes it from the source cell. If addition fails, the animal stays
     * in the source cell. Thread‑safe (locks both cells implicitly).
     * </p>
     *
     * @param source      the current cell
     * @param destination the target cell
     * @return {@code true} if the move was successful, {@code false} otherwise
     */
    protected boolean safeMove(Cell source, Cell destination) {
        return safeAddTo(destination) ? safePollFrom(source) : !safePollFrom(destination) ;
    }

    /**
     * Safely adds this animal to the given cell if capacity allows.
     * <p>
     * Checks that the number of organisms of this type in the cell does not
     * exceed {@code maxCountInCell}. If space is available, adds the animal
     * to the cell's residents. Thread‑safe (acquires the cell's lock).
     * </p>
     *
     * @param cell the target cell
     * @return {@code true} if the animal was added, {@code false} otherwise
     */
    protected boolean safeAddTo(Cell cell) {
        cell.getLock().lock();
        try {
            Organisms organisms = cell
                    .getResidents()
                    .get(getType());
            int maxCount = getLimit().getMaxCountInCell();
            int size = organisms.size();
            return size < maxCount && organisms.add(this);
        } finally {
            cell.getLock().unlock();
        }
    }
    /**
     * Safely removes this animal from the given cell.
     * <p>
     * Verifies that the animal is present in the cell, then removes it.
     * Thread‑safe (acquires the cell's lock).
     * </p>
     *
     * @param cell the cell from which to remove the animal
     * @return {@code true} if the animal was present and removed, {@code false} otherwise
     */
    protected boolean safePollFrom(Cell cell) {
        cell.getLock().lock();
        try {
            Residents residents = cell.getResidents();
            Organisms organisms = residents.get(getType());
            return isHere(cell) && organisms.remove(this);
        } finally {
            cell.getLock().unlock();
        }
    }
}
