package com.javarush.island.rukavanov.services;

import com.javarush.island.rukavanov.config.OrganismType;
import com.javarush.island.rukavanov.entity.island.Island;
import com.javarush.island.rukavanov.entity.island.Location;
import com.javarush.island.rukavanov.renderer.Renderer;

import java.util.LinkedHashMap;
import java.util.Map;

public class StatisticsService {
    private final Renderer renderer;

    public StatisticsService(Renderer renderer) {

        this.renderer = renderer;

    }

    public void process(Island island) {

        Map<String, Integer> statistics =

                collectStatistics(island);

        renderer.render(statistics);

    }

    private Map<String, Integer> collectStatistics(Island island) {

        Map<String, Integer> result =

                new LinkedHashMap<>();

        for (OrganismType type : OrganismType.values()) {

            result.put(type.getIcon(), 0);

        }

        for (int y = 0; y < island.getHeight(); y++) {

            for (int x = 0; x < island.getWidth(); x++) {

                Location location = island.get(x, y);

                location.lock();

                try {

                    for (OrganismType type : OrganismType.values()) {

                        int current =

                                result.get(type.getIcon());

                        current += location.count(type);

                        result.put(type.getIcon(), current);

                    }

                } finally {

                    location.unlock();

                }

            }

        }

        return result;

    }
}
