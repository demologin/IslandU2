package com.javarush.island.baksalyar.entity.interfaces;


import com.javarush.island.baksalyar.entity.map.Cell;
/**
 * Defines the eating behavior for organisms in the simulation.
 * <p>
 * Implementing classes are responsible for locating and consuming food sources
 * within a given cell, as well as safely checking for the presence of food
 * without modifying the cell state.
 * </p>
 * <p>
 * Typically implemented by {@link com.javarush.island.baksalyar.entity.organizm.animals.Animal} subclasses to define their specific
 * dietary strategies (e.g., herbivore, carnivore, omnivore).
 * </p>
 */
public interface Eating {
    boolean eat(Cell currentCell);
     boolean safeFindFood(Cell currentCell);
}
