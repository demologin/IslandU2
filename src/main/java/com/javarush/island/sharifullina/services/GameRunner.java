package com.javarush.island.sharifullina.services;

import com.javarush.island.sharifullina.entity.FoodChances;
import com.javarush.island.sharifullina.entity.map.Island;

public class GameRunner {
    public static void main(String[] args) throws Exception {
        System.out.println(FoodChances.getFoodChance("Wolf"));
    }
}
