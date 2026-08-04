package com.javarush.island.baksalyar.entity.interfaces;


import com.javarush.island.baksalyar.entity.map.Cell;
/**
 * Defines the reproduction behavior for organisms in the simulation.
 * <p>
 * Implementing classes are responsible for creating offspring within a given cell.
 * The reproduction logic may involve checking whether the organism has reached
 * sufficient energy or saturation levels, ensuring that the cell has enough space
 * (respecting the maximum count per cell), and then creating one or more new
 * organism instances.
 * </p>
 * <p>
 * Typically implemented by {@link com.javarush.island.baksalyar.entity.organizm.animals.Animal} and {@link com.javarush.island.baksalyar.entity.organizm.plants.Plant} subclasses to define
 * their specific reproductive strategies (e.g., sexual vs. asexual reproduction,
 * energy cost, random chance).
 * </p>
 */
@FunctionalInterface
public interface Reproducible {
    boolean spawn(Cell currentCell);
}
