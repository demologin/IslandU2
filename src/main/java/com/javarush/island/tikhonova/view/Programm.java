package com.javarush.island.tikhonova.view;

import com.javarush.island.tikhonova.WriteInFile;
import com.javarush.island.tikhonova.entity.Game;
import com.javarush.island.tikhonova.entity.map.GameMap;
import com.javarush.island.tikhonova.entity.map.StatisticOrganizm;
import com.javarush.island.tikhonova.services.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Programm extends Thread {

    GameMap gameMap = new GameMap();
    Game game = new Game(gameMap);

    public void printOrganizm() {
        game.getGameMap().InitialStateField();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for (int i = 0; i < StatisticOrganizm.countOrganizmStatistic.length; i++) {
            System.out.print(StatisticOrganizm.symbols[i] + " - " + StatisticOrganizm.countOrganizmStatistic[i] + " | ");
        }
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        threadGame();
    }

    public void threadGame() {
        List<Runnable> list = new ArrayList<>();
        list.add(new EatingService(game));
        list.add(new DyingService(game));
        list.add(new ReproductionService(game));
        list.add(new StepService(game));
        list.add(new RandomFillService(game));
        GameServiceProcessor gameServiceProcessor = new GameServiceProcessor(game, list);
        gameServiceProcessor.start();
    }
}
