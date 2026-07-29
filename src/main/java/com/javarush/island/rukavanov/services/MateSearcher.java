package com.javarush.island.rukavanov.services;

import com.javarush.island.rukavanov.config.OrganismType;
import com.javarush.island.rukavanov.entity.island.Location;

public class MateSearcher {
    public static boolean hasPair(Location location, OrganismType type) {
        return location.count(type) >= 2;
    }
}
