package com.javarush.island.tikhonova.entity.map;

import com.javarush.island.tikhonova.entity.organizm.Organizm;
import com.javarush.island.tikhonova.entity.organizm.animals.herbivores.*;
import com.javarush.island.tikhonova.entity.organizm.animals.predators.*;
import com.javarush.island.tikhonova.entity.organizm.plants.Grass;

import java.util.HashMap;
import java.util.Iterator;

public class CellMap {
    private int indexX;
    private int indexY;

    public CellMap(int indexX, int indexY) {
        this.indexX = indexX;
        this.indexY = indexY;
    }

    public Object monitor() {
        return this;
    }

    public int getIndexX() {
        return indexX;
    }

    public int getIndexY() {
        return indexY;
    }

    private HashMap<String, Organizm> organizmsInSectionWithKey = new HashMap<>();

    public synchronized HashMap<String, Organizm> getOrganizmsInSectionWithKey() {
        return organizmsInSectionWithKey;
    }

    public synchronized void addOrganizmInCell(String organizm, double weight, int countOrganizms) {
        switch (organizm) {
            case "buffalo" -> organizmsInSectionWithKey.put("buffalo", new Buffalo(weight, countOrganizms));
            case "caterpillar" -> organizmsInSectionWithKey.put("caterpillar", new Caterpillar(weight, countOrganizms));
            case "deer" -> organizmsInSectionWithKey.put("deer", new Deer(weight, countOrganizms));
            case "duck" -> organizmsInSectionWithKey.put("duck", new Duck(weight, countOrganizms));
            case "goat" -> organizmsInSectionWithKey.put("goat", new Goat(weight, countOrganizms));
            case "horse" -> organizmsInSectionWithKey.put("horse", new Horse(weight, countOrganizms));
            case "mouse" -> organizmsInSectionWithKey.put("mouse", new Mouse(weight, countOrganizms));
            case "rabbit" -> organizmsInSectionWithKey.put("rabbit", new Rabbit(weight, countOrganizms));
            case "sheep" -> organizmsInSectionWithKey.put("sheep", new Sheep(weight, countOrganizms));
            case "wild boar" -> organizmsInSectionWithKey.put("wild boar", new WildBoar(weight, countOrganizms));
            case "bear" -> organizmsInSectionWithKey.put("bear", new Bear(weight, countOrganizms));
            case "boa constrictor" ->
                    organizmsInSectionWithKey.put("boa constrictor", new BoaConstrictor(weight, countOrganizms));
            case "eagle" -> organizmsInSectionWithKey.put("eagle", new Eagle(weight, countOrganizms));
            case "fox" -> organizmsInSectionWithKey.put("fox", new Fox(weight, countOrganizms));
            case "wolf" -> organizmsInSectionWithKey.put("wolf", new Wolf(weight, countOrganizms));
            default -> organizmsInSectionWithKey.put("grass", new Grass(weight, countOrganizms));
        }
    }

    public void deleteOrganizm(String name) {
        organizmsInSectionWithKey.remove(name);
    }
}
