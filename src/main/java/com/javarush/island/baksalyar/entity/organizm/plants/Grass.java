package com.javarush.island.baksalyar.entity.organizm.plants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.javarush.island.baksalyar.entity.annotations.FinalOrganism;
import com.javarush.island.baksalyar.entity.map.Cell;
import com.javarush.island.baksalyar.entity.organizm.Limit;
import com.javarush.island.baksalyar.util.Rnd;

@FinalOrganism
public class Grass extends Plant {

    @JsonCreator
    public Grass(@JsonProperty("name") String name, @JsonProperty("limit") Limit limit, @JsonProperty("icon") String icon) {
        super(name, limit, icon);
    }

    @Override
    public boolean eat(Cell currentCell) {
        return false;
    }

    @Override
    public boolean safeFindFood(Cell currentCell) {
        return false;
    }

    @Override
    public boolean move(Cell startCell) {
        return false;
    }

    @Override
    public boolean spawn(Cell cell) {
        this.safeChangeWeight(cell, getPercentPlantGrow());
        boolean born = false;
        for (int i = 0; i < 6; i++) {
            Cell neighborCell = cell.getNextCell().get(Rnd.random(0, cell.getNextCell().size()));
            born = safePlantPropagation(neighborCell);
        }
        return born;
    }
}
