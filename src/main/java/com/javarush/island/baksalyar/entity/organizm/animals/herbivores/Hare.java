package com.javarush.island.baksalyar.entity.organizm.animals.herbivores;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.javarush.island.baksalyar.entity.annotations.FinalOrganism;
import com.javarush.island.baksalyar.entity.organizm.Limit;
/**
 * Represents a Hare (rabbit) organism in the simulation.
 * <p>
 * Hares are herbivorous animals that consume plants (grass, etc.).
 * They move, reproduce, and serve as prey for carnivorous organisms.
 * This class is a concrete leaf implementation marked as {@link FinalOrganism},
 * indicating it should not have subclasses.
 * </p>
 */
@FinalOrganism
public class Hare  extends Herbivore {
    @JsonCreator
    public Hare(@JsonProperty("name") String name, @JsonProperty("limit") Limit limit, @JsonProperty("icon") String icon) {
        super(name, limit, icon);
    }
}
