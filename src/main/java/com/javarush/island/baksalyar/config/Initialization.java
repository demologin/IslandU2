package com.javarush.island.baksalyar.config;

import com.javarush.island.baksalyar.entity.map.Cell;
import com.javarush.island.baksalyar.entity.organizm.Organism;

import java.util.List;
/**
 * Defines the contract for classes responsible for populating the game map with organisms.
 * <p>
 * Implementations of this interface are used during map initialization to fill each
 * {@link Cell} with a collection of organisms based on a probability threshold.
 * The interface also provides a method to retrieve all available organism prototypes,
 * which can be used for registration, configuration, or debug purposes.
 * </p>
 * <p>
 * The typical implementation (e.g., {@link com.javarush.island.baksalyar.creators.EntityCreator}) iterates over the organism
 * prototypes defined in {@link Setting#PROTOTYPES}, checks their participation flag,
 * and decides randomly whether to add clones to a cell. The number of added organisms
 * is random and bounded by the cell's maximum capacity.
 * </p>
 * <p>
 * This interface is used by when constructing the initial
 * </p>
 */
public interface Initialization {
    /**
     * Populates the given cell with organisms.
     * The decision to add each organism type is probabilistic.
     *
     * @param cell              the cell to fill
     * @param percentProbably   probability (0.0–1.0) of adding each type
     */
    void fill(Cell cell, double percentProbably);
    /**
     * Returns all organism prototypes available for initialization.
     * May be empty if prototypes are managed elsewhere.
     *
     * @return list of prototype organisms
     */
    List<Organism> getAllPrototypes();
}
