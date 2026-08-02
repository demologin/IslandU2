package com.javarush.island.rukavanov.services;

import com.javarush.island.rukavanov.config.OrganismType;
import com.javarush.island.rukavanov.entity.animals.Animal;
import com.javarush.island.rukavanov.entity.island.Island;
import com.javarush.island.rukavanov.entity.island.Location;
import com.javarush.island.rukavanov.util.RandomUtil;

public class MoveService implements IslandService {

    @Override
    public void process(Island island) {

        for (int y = 0; y < island.getHeight(); y++) {

            for (int x = 0; x < island.getWidth(); x++) {

                Location from = island.get(x, y);

                for (OrganismType type : OrganismType.animals()) {

                    for (Animal animal : from.getAnimalsSnapshot(type)) {

                        if (!animal.isAlive()) {

                            continue;

                        }

                        int speed = animal.getData().getSpeed();

                        if (speed == 0) {

                            continue;

                        }

                        Direction direction =

                                Direction.random();

                        int distance =

                                RandomUtil.random(1, speed);

                        Location target =

                                MoveHelper.getTargetLocation(

                                        island,

                                        from,

                                        direction,

                                        distance

                                );

                        if (target == from) {

                            continue;

                        }

                        from.moveOrganismTo(

                                target,

                                animal

                        );

                    }

                }

            }

        }

    }

}
