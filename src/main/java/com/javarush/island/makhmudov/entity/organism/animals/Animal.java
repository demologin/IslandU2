package com.javarush.island.makhmudov.entity.organism.animals;

import com.javarush.island.makhmudov.config.Setting;
import com.javarush.island.makhmudov.api.entity.Eating;
import com.javarush.island.makhmudov.api.entity.Movable;
import com.javarush.island.makhmudov.api.entity.Reproducible;
import com.javarush.island.makhmudov.entity.map.Cell;
import com.javarush.island.makhmudov.entity.organism.Limit;
import com.javarush.island.makhmudov.entity.organism.Organism;
import com.javarush.island.makhmudov.entity.organism.Organisms;

public abstract class Animal extends Organism implements Eating, Reproducible, Movable {
    public Animal(String name, String icon, Limit limit) {
        super(name, icon, limit);
    }
    @Override
    public boolean eat(Cell currentCell) {
        if (safeFindFood(currentCell)) {
            return true;
        }
        if (getWeight() > 0) {
            return safeChangeWeight(currentCell, -1 * Setting.get().life.getPercentAnimalSlim());
        }
        return !safeDie(currentCell);
    }
    @Override
    public boolean move(Cell startCell) {
        int countStep = this.getLimit().getMaxSpeed();
        Cell destinationCell = startCell.getNextCell(countStep);
        return safeMove(startCell, destinationCell);
    }

    @Override
    public boolean spawn(Cell cell) {
        return safeSpawnAnimal(cell);
    }

    private boolean safeSpawnAnimal(Cell cell) {
        cell.getLock().lock();
        try {
            Organisms organisms = cell.getResidents().get(getType());
            double maxWeight = getLimit().getMaxWeight();
            if (isHere(cell) && getWeight() > maxWeight / 2 &&
                    organisms.contains(this) &&
                    (organisms.getLimit().getFlockSize() > 1 || organisms.size() >= 2) &&
                    organisms.size() < getLimit().getMaxCountInCell()) {
                double childWeight = getWeight() / 2;
                setWeight(childWeight / 2);
                Organism clone = Organism.clone(this);
                clone.setWeight(childWeight);
                organisms.add(clone);
                return true;
            }
        } finally {
            cell.getLock().unlock();
        }
        return false;
    }
}
