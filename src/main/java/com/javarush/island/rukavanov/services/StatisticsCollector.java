package com.javarush.island.rukavanov.services;

import com.javarush.island.rukavanov.config.OrganismType;
import com.javarush.island.rukavanov.entity.island.Island;
import com.javarush.island.rukavanov.entity.island.IslandSnapshot;
import com.javarush.island.rukavanov.entity.island.Location;

public class StatisticsCollector {
    public IslandSnapshot collect(Island island, long tick) {

        IslandSnapshot snapshot = new IslandSnapshot();

        snapshot.setTick(tick);

        for (OrganismType type : OrganismType.values()) {

            int total = 0;
            for (int y = 0; y < island.getHeight(); y++) {
                for (int x = 0; x < island.getWidth(); x++) {

                    Location location = island.get(x, y);
                    total += location.count(type);

                }
            }
            snapshot.put(type, total);
        }
        return snapshot;
    }
}
