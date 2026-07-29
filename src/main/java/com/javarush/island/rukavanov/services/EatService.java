package com.javarush.island.rukavanov.services;

import com.javarush.island.rukavanov.config.OrganismType;
import com.javarush.island.rukavanov.entity.Organism;
import com.javarush.island.rukavanov.entity.animals.Animal;
import com.javarush.island.rukavanov.entity.island.Island;
import com.javarush.island.rukavanov.entity.island.Location;
import com.javarush.island.rukavanov.util.RandomUtil;

import java.util.Map;

public class EatService extends AbstractIslandService {

    @Override
    protected void processLocation(Location location, Island island) {

        for (OrganismType hunterType : OrganismType.animals()) {

            for (Animal hunter : location.getAnimals(hunterType)) {

                if (!hunter.isAlive()) {
                    continue;
                }

                if (!hunter.isHungry()) {
                    continue;
                }

                for (Map.Entry<OrganismType, Integer> entry : FoodSearcher.getAvailableFood(hunterType).entrySet()) {

                    OrganismType foodType = entry.getKey();
                    int probability = entry.getValue();
                    if (!RandomUtil.chance(probability)) {
                        continue;
                    }

                    Organism victim = location.takeRandomOrganism(foodType);

                    if (victim == null) {
                        continue;
                    }

                    hunter.eat(victim);
                    break;
                }

            }

        }

    }

}
