package com.javarush.island.baksalyar.entity.organizm.animals.herbivores;


import com.javarush.island.baksalyar.config.Setting;
import com.javarush.island.baksalyar.entity.map.Cell;
import com.javarush.island.baksalyar.entity.map.Residents;
import com.javarush.island.baksalyar.entity.organizm.Limit;
import com.javarush.island.baksalyar.entity.organizm.Organism;
import com.javarush.island.baksalyar.entity.organizm.Organisms;
import com.javarush.island.baksalyar.entity.organizm.animals.Animal;
import com.javarush.island.baksalyar.entity.organizm.plants.Plant;

import java.util.Arrays;
import java.util.Iterator;
/**
 * Abstract base class for herbivorous animals in the simulation.
 * <p>
 * Herbivores feed exclusively on plants ({@link Plant}) found in the same cell.
 * The eating probability is determined by a ratio of the herbivore's combat score
 * (weight + power + maxSpeed) to the plant's equivalent. The herbivore's weight
 * increases by the consumed plant's weight, and the plant is removed if its
 * weight drops to zero.
 * </p>
 * <p>
 * Reproduction is not yet implemented (returns {@code false}).
 * </p>
 */
public abstract class Herbivore extends Animal {
    protected Herbivore(String name, Limit limit, String icon) {
        super(name, limit, icon);
    }
    /**
     * Searches for food (plants) in the current cell and consumes them.
     * <p>
     * For each plant in the cell, the method calculates a chance based on
     * the herbivore's and plant's combat scores. If random chance succeeds,
     * the herbivore consumes as much of the plant's weight as needed to
     * reach saturation (maxFood). The plant's weight is reduced accordingly;
     * if it becomes zero, the plant is removed from the cell.
     * </p>
     * <p>
     * The method is thread-safe (acquires the cell's lock).
     * </p>
     *
     * @param currentCell the cell to search for food
     * @return {@code true} if any food was consumed, {@code false} otherwise
     */
    @Override
    public boolean safeFindFood(Cell currentCell) {
        currentCell.getLock().lock();
        boolean foodFound = false;
        try {
            double needFood = this.getNeedFood();
            Residents residents = currentCell.getResidents();
            Organism[] participants = Arrays.stream(Setting.get().getPARTICIPANTS()).filter(c -> c instanceof Plant).toArray(Organism[]::new);
            for (Organism participant : participants) {
                Organisms organisms = residents.get(participant.getType());
                for (Iterator<Organism> organismIterator = organisms.iterator(); organismIterator.hasNext(); ) {
                    Organism organism = organismIterator.next();
                    double predatorScore = this.getWeight() + this.getPower() + this.getLimit().getMaxSpeed();
                    double preyScore = organism.getWeight() + organism.getPower()  + organism.getLimit().getMaxSpeed();
                    double chance = predatorScore / (predatorScore + preyScore);
                    double chance2 = Math.random() ;
                    if (chance2 > chance) {continue;}
                    double foodWeight = organism.getWeight();
                    double delta = Math.min(foodWeight, needFood);
                    setWeight(getWeight() + delta);
                    organism.setWeight(foodWeight - delta);
                    if (organism.getWeight() <= 0) {organismIterator.remove();}
                    needFood -= delta;
                    foodFound = true;
                    if (needFood <= 0) {break;}
                }
            }
        }
        finally {currentCell.getLock().unlock();}
        return foodFound;
    }

    @Override
    public boolean spawn(Cell currentCell) {
        return false;
    }
}
