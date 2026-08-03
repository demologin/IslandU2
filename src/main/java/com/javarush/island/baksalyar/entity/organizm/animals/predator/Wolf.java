package com.javarush.island.baksalyar.entity.organizm.animals.predator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.javarush.island.baksalyar.entity.annotations.FinalOrganism;
import com.javarush.island.baksalyar.entity.map.Cell;
import com.javarush.island.baksalyar.entity.organizm.Limit;

@FinalOrganism
public class Wolf extends Predator{
    @JsonCreator
    public Wolf(@JsonProperty("name") String name, @JsonProperty("limit") Limit limit, @JsonProperty("icon") String icon) {
        super(name, limit, icon);
    }

    @Override
    public boolean spawn(Cell currentCell) {
        return false;
    }
}
