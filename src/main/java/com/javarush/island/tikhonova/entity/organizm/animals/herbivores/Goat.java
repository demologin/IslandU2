package com.javarush.island.tikhonova.entity.organizm.animals.herbivores;

import com.javarush.island.tikhonova.entity.map.CellMap;
import com.javarush.island.tikhonova.entity.map.GameMap;
import com.javarush.island.tikhonova.entity.map.Map;
import com.javarush.island.tikhonova.entity.organizm.Limit;
import com.javarush.island.tikhonova.entity.organizm.animals.Animal;

public class Goat extends Herbivore {
    public Goat(double weight, int countGoat) {

        super("goat", weight, "\uD83D\uDC10", new Limit(3, 10,
                Map.weightMaxForInstance[4], Map.weightMinForInstance[4], Map.maxNumbersInstanceInCell[4]), countGoat);
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
