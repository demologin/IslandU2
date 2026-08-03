package com.javarush.island.baksalyar.entity.organizm.animals.predator;


import com.javarush.island.baksalyar.config.Setting;
import com.javarush.island.baksalyar.entity.map.Cell;
import com.javarush.island.baksalyar.entity.map.Residents;
import com.javarush.island.baksalyar.entity.organizm.Limit;
import com.javarush.island.baksalyar.entity.organizm.Organism;
import com.javarush.island.baksalyar.entity.organizm.Organisms;
import com.javarush.island.baksalyar.entity.organizm.animals.Animal;

import java.util.Iterator;
/**
 * Abstract base class for predatory animals that hunt other animals.
 * <p>
 * Predators search for prey (other {@link Animal} instances of different types)
 * in the same cell. The hunting success chance is determined by the ratio of
 * the predator's combat score (weight + power + maxSpeed) to the prey's score.
 * On a successful hunt, the predator consumes the prey's weight and the prey
 * is removed if its weight drops to zero.
 * </p>
 * <p>
 * Thread-safe (acquires the cell's lock during hunting).
 * </p>
 */
public abstract class Predator extends Animal {
    public Predator(String name, Limit limit, String icon) {
        super(name, limit, icon);
    }
    /**
     * Hunts for prey (other animals) in the current cell.
     * <p>
     * Iterates over all participating organism types, skips the predator's own
     * type, and attacks any animal. Success depends on a random chance based
     * on combat scores. Consumes prey weight until the predator is saturated.
     * </p>
     *
     * @param currentCell the cell to hunt in
     * @return {@code true} if any food was consumed, {@code false} otherwise
     */
    @Override
    public boolean safeFindFood(Cell currentCell) {
        currentCell.getLock().lock();
        boolean foodFound = false;
        try {
            double needFood = this.getNeedFood();
            Residents residents = currentCell.getResidents();
            Organism[] participants = Setting.get().getPARTICIPANTS();
            for (Organism participant : participants) {
                Organisms organisms = residents.get(participant.getType());
                for (Iterator<Organism> organismIterator = organisms.iterator(); organismIterator.hasNext(); ) {
                    Organism organism = organismIterator.next();
                    if (organism.getType() != this.getType() && organism instanceof Animal) {
                        double predatorScore = this.getWeight() + this.getPower() + this.getLimit().getMaxSpeed();
                        double preyScore = organism.getWeight() + organism.getPower()  + organism.getLimit().getMaxSpeed();
                        double chance = predatorScore / (predatorScore + preyScore);
                        double chance2 = Math.random() ;
                        if(chance2 < chance) {
                            double foodWeight = organism.getWeight();
                            double delta = Math.min(foodWeight, needFood);
                            setWeight(getWeight() + delta);
                            organism.setWeight(foodWeight - delta);
                            if (organism.getWeight() <= 0) {
                                organismIterator.remove();
                            }
                            needFood -= delta;
                            foodFound = true;
                            if (needFood <= 0) {
                                break;
                            }
                        }
                    }
                }
            }
        }finally {
            currentCell.getLock().unlock();
        }
        return foodFound;
    }

    @Override
    public boolean spawn(Cell currentCell) {
        return false;
    }
}
