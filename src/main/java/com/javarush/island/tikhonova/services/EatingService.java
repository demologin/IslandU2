package com.javarush.island.tikhonova.services;

import com.javarush.island.tikhonova.WriteInFile;
import com.javarush.island.tikhonova.entity.Game;
import com.javarush.island.tikhonova.entity.map.CellMap;
import com.javarush.island.tikhonova.entity.map.GameMap;
import com.javarush.island.tikhonova.entity.map.Map;
import com.javarush.island.tikhonova.entity.organizm.Organizm;
import com.javarush.island.tikhonova.entity.organizm.animals.herbivores.*;
import com.javarush.island.tikhonova.entity.organizm.animals.predators.*;

import java.io.IOException;
import java.util.Random;

public class EatingService extends AbstractService {


    public EatingService(Game game) {
        super(game);
    }


    public void eatService(CellMap cell) {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int whoEat = random.nextInt(Map.nameOrganizms.length - 1);
            String name = Map.nameOrganizms[whoEat];
            if (cell.getOrganizmsInSectionWithKey().containsKey(name)) {
                cell.getOrganizmsInSectionWithKey().get(name).eat(cell, name);
                break;
            }
        }

    }

    @Override
    public void run() {
        game.getGameMap().getStreamCells().forEach(this::eatService);
    }
}
