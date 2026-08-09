package com.javarush.island.tikhonova.entity.organizm.animals.predators;

import com.javarush.island.tikhonova.entity.map.CellMap;
import com.javarush.island.tikhonova.entity.map.GameMap;
import com.javarush.island.tikhonova.entity.map.Map;
import com.javarush.island.tikhonova.entity.organizm.Limit;

public class BoaConstrictor extends PredatorOrganizm {
    public BoaConstrictor(double weight, int countBoaConstrictor) {
        super("boa constrictor", weight, "\uD83D\uDC0D", new Limit(1, 3,
                Map.weightMaxForInstance[11], Map.weightMinForInstance[11], Map.maxNumbersInstanceInCell[11]), countBoaConstrictor);
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


}
