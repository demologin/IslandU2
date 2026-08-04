package com.javarush.island.baksalyar.entity.organizm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
/**
 * A data container that holds the numerical limits and constraints for an organism type.
 * <p>
 * Each organism (e.g., Wolf, Rabbit, Grass) has an associated {@code Limit} object
 * that defines its maximum allowable values for various attributes. These limits
 * are used throughout the simulation to enforce rules such as:
 * <ul>
 *   <li>How many individuals of this type can occupy a single cell</li>
 *   <li>Maximum weight (or energy) an organism can reach</li>
 *   <li>Maximum movement speed (affects how many cells it can move per tick)</li>
 *   <li>Maximum food capacity (saturation level)</li>
 *   <li>Preferred flock size (for animals that exhibit group behavior)</li>
 * </ul>
 * </p>
 * <p>
 * The class provides standard getters and setters for each field (not shown in the
 * snippet) to allow modification via the settings UI and persistence to JSON files.
 * </p>
 * <p>
 * Instances of {@code Limit} are typically created and stored inside an {@link Organism}
 * object and can be accessed via {@link Organism#getLimit()}.
 * </p>
 */
@Getter
@AllArgsConstructor
@Jacksonized
@Builder
@Setter
public class Limit {
    private int maxCountInCell;
    private double maxWeight;
    private int maxSpeed;
    private double maxFood;
    private int flockSize;
}