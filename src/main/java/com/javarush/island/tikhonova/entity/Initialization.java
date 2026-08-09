package com.javarush.island.tikhonova.entity;

import com.javarush.island.tikhonova.entity.map.CellMap;
import com.javarush.island.tikhonova.entity.organizm.Organizm;

import java.util.ArrayList;

public interface Initialization {
    void fill(CellMap cellMap, double percentProbably);
    ArrayList<Organizm> allOrganozms();
}
