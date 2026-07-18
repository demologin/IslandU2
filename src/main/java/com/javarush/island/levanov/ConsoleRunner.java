package com.javarush.island.levanov;

import com.javarush.island.levanov.entity.Creator;
import com.javarush.island.levanov.entity.map.GameMap;
import com.javarush.island.levanov.view.ConsoleView;

public class ConsoleRunner {
    public static void main(String[] args) {
        Creator creator = new Creator();
        GameMap gameMap = creator.createGameMap();
        ConsoleView console = new ConsoleView(gameMap);
        console.showStatistics();
    }
}
