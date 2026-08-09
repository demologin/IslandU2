package com.javarush.island.tikhonova.entity.organizm.animals.predators;

import com.javarush.island.tikhonova.entity.map.CellMap;
import com.javarush.island.tikhonova.entity.map.GameMap;
import com.javarush.island.tikhonova.entity.map.Map;
import com.javarush.island.tikhonova.entity.organizm.Limit;

public class Bear extends PredatorOrganizm {
    public Bear(double weight, int countBear) {

        super("bear", weight, "\uD83D\uDC3B", new Limit(2, 80,
                Map.weightMaxForInstance[10], Map.weightMinForInstance[10], Map.maxNumbersInstanceInCell[10]), countBear);
    }

    @Override
    public void eat(CellMap cellMap, String name) {
        super.eat(cellMap, name);
    }

    @Override
    public boolean dying(CellMap cellMap, String name) {
        if (cellMap.getOrganizmsInSectionWithKey().get(name).getWeight() < cellMap.getOrganizmsInSectionWithKey().get(name).getLimit().getMinWeight() * cellMap.getOrganizmsInSectionWithKey().get(name).getCountOrganizms()) {
            return true;
        }
        return false;
    }

    @Override
    public void reproduction(CellMap cellMap, String name) {
        super.reproduction(cellMap, name);
    }

    @Override
    public void going(GameMap gameMap, CellMap cellMap, String name) {
        super.going(gameMap, cellMap, name);
    }

    @Override
    public String toString() {
        return
                "'" + this.getSymbols() + '\'' + " " +
                        this.getCountOrganizms();
    }
}
