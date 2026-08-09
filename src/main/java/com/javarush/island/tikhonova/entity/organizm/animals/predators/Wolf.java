package com.javarush.island.tikhonova.entity.organizm.animals.predators;

import com.javarush.island.tikhonova.entity.map.CellMap;
import com.javarush.island.tikhonova.entity.map.GameMap;
import com.javarush.island.tikhonova.entity.map.Map;
import com.javarush.island.tikhonova.entity.organizm.Limit;

public class Wolf extends PredatorOrganizm {
    public Wolf(double weight, int countWolf) {
        super("wolf", weight, "\uD83D\uDC3A", new Limit(3, 8,
                Map.weightMaxForInstance[14], Map.weightMinForInstance[14], Map.maxNumbersInstanceInCell[14]), countWolf);
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
