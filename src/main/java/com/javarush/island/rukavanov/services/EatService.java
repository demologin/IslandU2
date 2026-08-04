package com.javarush.island.rukavanov.services;

import com.javarush.island.rukavanov.config.OrganismType;
import com.javarush.island.rukavanov.entity.Organism;
import com.javarush.island.rukavanov.entity.animals.Animal;
import com.javarush.island.rukavanov.entity.island.Island;
import com.javarush.island.rukavanov.entity.island.Location;
import com.javarush.island.rukavanov.util.RandomUtil;

public class EatService {
    public void process(Location location, Island island) {

        for (OrganismType type : OrganismType.animals()) {

            for (Animal hunter : location.getAnimalsSnapshot(type)) {

                if (!hunter.isAlive()) {

                    continue;

                }

                Organism food =

                        FoodSearcher.findFood(location, hunter);

                if (food == null) {

                    continue;

                }

                int chance =

                        hunter.getChanceToEat(food.getData());

                if (!RandomUtil.chance(chance)) {

                    continue;

                }

                location.removeOrganism(food);

                hunter.eat(food);

            }

        }

    }
}
