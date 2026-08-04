package com.javarush.island.baksalyar.entity.organizm.plants;

import com.javarush.island.baksalyar.entity.map.Cell;
import com.javarush.island.baksalyar.entity.organizm.Limit;
import com.javarush.island.baksalyar.entity.organizm.Organism;
import com.javarush.island.baksalyar.entity.organizm.Organisms;
import lombok.Getter;
import lombok.Setter;
/**
 * Abstract base class for all plants in the simulation.
 * <p>
 * Plants are stationary organisms that do not move and do not actively hunt for food.
 * They reproduce through a process of cloning when conditions are favorable:
 * sufficient weight (energy) and available space in the cell.
 * </p>
 * <p>
 * The reproduction logic is encapsulated in {@link #safePlantPropagation(Cell)},
 * which is thread-safe and respects the cell's maximum capacity.
 * </p>
 */
@Getter
@Setter
public abstract class Plant extends Organism {

    private int percentPlantGrow = 15;

    public Plant(String name, Limit limit, String icon) {
        super(name, limit, icon);
    }
    /**
     * Attempts to propagate (reproduce) within the given cell.
     * <p>
     * Reproduction occurs if:
     * <ul>
     *   <li>The current number of plants of this type in the cell is less than
     *       the maximum allowed ({@link Limit#getMaxCountInCell()})</li>
     *   <li>This plant's current weight exceeds half of its maximum weight
     *       ({@link Limit#getMaxWeight()})</li>
     * </ul>
     * When conditions are met, a new plant is created as a clone of the current one,
     * receiving 10% of the parent's weight. The parent's weight remains unchanged.
     * </p>
     * <p>
     * This method is thread-safe – it acquires the cell's lock before modifying
     * the residents collection.
     * </p>
     *
     * @param cell the cell in which to propagate
     * @return {@code true} if a new plant was successfully added to the cell,
     *         {@code false} otherwise
     */
    public boolean safePlantPropagation(Cell cell) {
        Limit limit = getLimit();
        cell.getLock().lock();
        try {
            Organisms plants = cell.getResidents().get(getType());
            if (plants.size() < limit.getMaxCountInCell() &&
                    getWeight() > limit.getMaxWeight() / 2
            ) {
                Organism newPlant = Organism.clone(this);
                double childWeight = getWeight() / 10;
                newPlant.setWeight(childWeight);
                return plants.add(newPlant);
            }
        } finally {
            cell.getLock().unlock();
        }
        return false;
    }
}
