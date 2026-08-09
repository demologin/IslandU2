package com.javarush.island.tikhonova.entity.organizm.animals.herbivores;

import com.javarush.island.tikhonova.entity.map.CellMap;
import com.javarush.island.tikhonova.entity.map.GameMap;
import com.javarush.island.tikhonova.entity.map.Map;
import com.javarush.island.tikhonova.entity.organizm.Limit;
import com.javarush.island.tikhonova.entity.organizm.animals.Animal;

public class Rabbit extends Herbivore {
    public Rabbit(double weight, int countRabbit) {

        super("rabbit", weight, "\uD83D\uDC07", new Limit(2, 0.45,
                Map.weightMaxForInstance[7], Map.weightMinForInstance[7], Map.maxNumbersInstanceInCell[7]), countRabbit);
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
