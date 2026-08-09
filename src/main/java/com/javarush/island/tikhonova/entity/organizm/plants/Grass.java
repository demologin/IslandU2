package com.javarush.island.tikhonova.entity.organizm.plants;

import com.javarush.island.tikhonova.entity.map.CellMap;
import com.javarush.island.tikhonova.entity.map.GameMap;
import com.javarush.island.tikhonova.entity.map.Map;
import com.javarush.island.tikhonova.entity.organizm.Limit;

public class Grass extends Plant {
    public Grass(double weight, int countGrass) {

        super("grass", weight, "\uD83C\uDF3F", new Limit(0, 0,
                Map.weightMaxForInstance[15], Map.weightMinForInstance[15], Map.maxNumbersInstanceInCell[15]), countGrass);
    }

    @Override
    public void reproduction(CellMap cellMap, String name) {
        super.reproduction(cellMap, name);
    }

    @Override
    public boolean dying(CellMap cellMap, String name) {
        if (cellMap.getOrganizmsInSectionWithKey().get(name).getWeight() < cellMap.getOrganizmsInSectionWithKey().get(name).getLimit().getMinWeight() * cellMap.getOrganizmsInSectionWithKey().get(name).getCountOrganizms()) {
            return true;
        }
        return false;
    }

    @Override
    public void eat(CellMap cellMap, String name) {

    }

    @Override
    public void going(GameMap gameMap, CellMap cell, String name) {

    }
}
