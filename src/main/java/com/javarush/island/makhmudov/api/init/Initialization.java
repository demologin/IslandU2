package com.javarush.island.makhmudov.api.init;

import com.javarush.island.makhmudov.entity.map.Cell;
import com.javarush.island.makhmudov.entity.organism.Organism;

import java.util.List;

public interface Initialization {

    void fill(Cell cell, double persentProbably);

    List<Organism> getAllPrototypes();
}
