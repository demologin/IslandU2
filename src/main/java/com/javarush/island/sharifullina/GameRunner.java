package com.javarush.island.sharifullina;

import com.javarush.island.sharifullina.entity.map.Game;
import com.javarush.island.sharifullina.entity.map.Island;
import com.javarush.island.sharifullina.services.ScheduleService;
import com.javarush.island.sharifullina.view.ConsoleView;

import static java.util.Arrays.sort;
import static java.util.Arrays.stream;

public class GameRunner {
    public static void main(String[] args){
        Island island = new Island(2, 2);
        ConsoleView view = new ConsoleView(island);
        Game game = new Game(island, view);
        view.show();
        ScheduleService scheduleService = new ScheduleService(game);
        new Thread(scheduleService).start();
    }
}
