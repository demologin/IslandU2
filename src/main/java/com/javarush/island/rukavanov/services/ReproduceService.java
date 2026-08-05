package com.javarush.island.rukavanov.services;

import com.javarush.island.rukavanov.config.OrganismType;
import com.javarush.island.rukavanov.config.Settings;
import com.javarush.island.rukavanov.entity.island.Location;
import com.javarush.island.rukavanov.factory.OrganismFactory;

public class ReproduceService {
    public void process(Location location) {

        for (OrganismType type : OrganismType.animals()) {

            if (!MateSearcher.hasPair(location, type)) {
                continue;
            }

            int freePlaces = type.getMaxCount() - location.count(type);

            if (freePlaces <= 0) {
                continue;
            }

            int babies = Math.min(Settings.BABIES_PER_BIRTH, freePlaces);

            for (int i = 0; i < babies; i++) {

                location.addOrganism(OrganismFactory.create(type));

            }
        }
    }
}
