package com.javarush.island.makhmudov.entity.organism.animals.herbivores;

import com.javarush.island.makhmudov.config.Setting;
import com.javarush.island.makhmudov.entity.map.Cell;
import com.javarush.island.makhmudov.entity.organism.Limit;
import com.javarush.island.makhmudov.entity.organism.Organism;
import com.javarush.island.makhmudov.entity.organism.Organisms;
import com.javarush.island.makhmudov.util.Rnd;
import com.javarush.island.makhmudov.api.annotation.DataType;

@DataType(name = "Гусеница", icon = "\uD83D\uDC1B", maxWeight = 0.01, maxCountInCell = 1000,
        flockSize = 100, maxSpeed = 0, maxFood = 0)
public class Caterpillar extends Herbivore {
    public Caterpillar(String name, String icon, Limit limit) {
        super(name, icon, limit);
    }

    @Override
    public boolean spawn(Cell cell) {
        this.safeChangeWeight(cell, Setting.get().life.getPercentPlantGrow());
        boolean born = false;
        for (int i = 0; i < 6; i++) {
            Cell neighborCell = cell.getNextCell(Rnd.random(0, 2));
            born |= safePlantPropagation(neighborCell);
        }
        return born;
    }

    private boolean safePlantPropagation(Cell cell) {
        Limit limit = getLimit();
        cell.getLock().lock();
        try {
            Organisms plants = cell.getResidents().get(getType());
            if (plants.size() < limit.getMaxCountInCell() &&
                    getWeight() > limit.getMaxWeight() / 2
            ) {
                Organism newPlant = Organism.clone(this);
                double childWeight = getWeight() / 10;
                newPlant.setWeight(childWeight);
                return plants.add(newPlant);
            }
        } finally {
            cell.getLock().unlock();
        }
        return false;
    }
}
