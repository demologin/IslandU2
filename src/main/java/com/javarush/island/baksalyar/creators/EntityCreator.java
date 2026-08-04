package com.javarush.island.baksalyar.creators;



import com.javarush.island.baksalyar.config.Initialization;
import com.javarush.island.baksalyar.config.Setting;
import com.javarush.island.baksalyar.entity.map.Cell;
import com.javarush.island.baksalyar.entity.map.Residents;
import com.javarush.island.baksalyar.entity.organizm.Organism;
import com.javarush.island.baksalyar.entity.organizm.Organisms;
import com.javarush.island.baksalyar.util.Rnd;

import java.util.List;

/**
 * Implementation of the {@link Initialization} interface responsible for populating
 * a game cell with organisms during map initialization.
 * <p>
 * For each organism prototype defined in {@link Setting#PROTOTYPES} that is marked
 * as participating ({@link Organism#isParticipates()}), the method decides whether
 * to add organisms to the cell based on a given probability. If selected, it adds
 * a random number (between 0 and the maximum allowed per cell, minus current count)
 * of clones of the prototype to the cell's resident list.
 * </p>
 * <p>
 * The cloning uses {@link Organism#clone(Organism)} to create new instances.
 * This class is stateless and designed for one-time use during game map creation.
 * </p>
 */
public class EntityCreator implements Initialization {

    /**
     * Fills the given cell with organisms according to the specified probability.
     * For each participating organism type, it rolls a random chance based on
     * {@code percentProbably}. If successful, it adds a random number (from 0 up to
     * the remaining capacity in the cell) of clones of that prototype.
     *
     * @param cell              the cell to populate
     * @param percentProbably   the probability (0.0–1.0) that a particular organism
     *                          type will be spawned in the cell. If the random value
     *                          falls below this threshold, organisms are added.
     */
    @Override
    public void fill(Cell cell, double percentProbably) {
        Residents residents = cell.getResidents();
        for (Organism prototype : Setting.PROTOTYPES) {
            if (!prototype.isParticipates()) {
                continue;
            }
            String type = prototype.getType();
            boolean fill = Rnd.get(percentProbably);
            if (fill) {
                Organisms organisms = residents.get(type);
                int currentCount = organisms.size();
                int max = prototype.getLimit().getMaxCountInCell() - currentCount;
                int count = Rnd.random(0, max);
                for (int i = 0; i < count; i++) {
                    organisms.add(Organism.clone(prototype));
                }
            }
        }
    }

    /**
     * Returns a list of all prototype organisms. This implementation returns an
     * empty list because the actual prototypes are accessed statically from
     * {@link Setting#PROTOTYPES} in the {@link #fill(Cell, double)} method.
     * This method is a placeholder and not used in the current logic.
     *
     * @return an empty list (default implementation)
     */
    @Override
    public List<Organism> getAllPrototypes() {
        return List.of();
    }
}
