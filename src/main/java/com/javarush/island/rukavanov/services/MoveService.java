package com.javarush.island.rukavanov.services;

import com.javarush.island.rukavanov.config.OrganismType;
import com.javarush.island.rukavanov.config.Settings;
import com.javarush.island.rukavanov.entity.animals.Animal;
import com.javarush.island.rukavanov.entity.island.Island;
import com.javarush.island.rukavanov.entity.island.Location;
import com.javarush.island.rukavanov.util.RandomUtil;

public class MoveService {

    public void process(Location location, Island island) {

        for (OrganismType type : OrganismType.animals()) {

            for (Animal animal : location.getAnimalsSnapshot(type)) {

                if (!animal.isAlive()) {

                    continue;

                }

                int speed = animal.getData().getSpeed();

                if (speed == 0) {

                    continue;

                }

                // Не каждое животное двигается каждый такт

                if (!RandomUtil.chance(Settings.MOVE_CHANCE)) {

                    continue;

                }

                Direction direction = Direction.random();

                int distance = RandomUtil.random(1, speed);

                Location target = MoveHelper.getTargetLocation(

                        island,

                        location,

                        direction,

                        distance

                );

                if (target == location) {

                    continue;

                }

                location.moveOrganismTo(target, animal);

            }

        }

    }

}
