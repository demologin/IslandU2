package com.javarush.island.baksalyar.entity.interfaces;


import com.javarush.island.baksalyar.entity.map.Cell;
/**
 * Defines the movement behavior for organisms in the simulation.
 * <p>
 * Implementing classes are responsible for determining and executing the logic
 * of moving an organism from its current cell to another cell (usually a neighbor).
 * The movement strategy may depend on environmental factors, such as the presence
 * of food, predators, or other organisms, and may include random walks,
 * directed movement, or pathfinding.
 * </p>
 * <p>
 * Typically implemented by {@link com.javarush.island.baksalyar.entity.organizm.animals.Animal} subclasses to define their specific
 * movement patterns.
 * </p>
 */
public interface Movement {
    boolean move(Cell startCell);
}
