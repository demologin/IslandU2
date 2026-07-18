package com.javarush.island.levanov.entity.map;

import com.javarush.island.levanov.entity.organisms.Organism;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cell {
    private Map<String, Set<Organism>> residents;

    public void setResidents(Map<String, Set<Organism>> residents) {
        this.residents = residents;
    }

    public Map<String, Set<Organism>> getResidents() {
        return residents;
    }
}
