package com.javarush.island.sharifullina;

import com.javarush.island.sharifullina.entity.map.Game;
import com.javarush.island.sharifullina.entity.map.Island;
import com.javarush.island.sharifullina.entity.organizm.Organizm;
import com.javarush.island.sharifullina.entity.organizm.Organizms;
import com.javarush.island.sharifullina.entity.organizm.Population;
import com.javarush.island.sharifullina.entity.organizm.animal.herbivore.Buffalo;
import com.javarush.island.sharifullina.entity.organizm.animal.herbivore.Goat;
import com.javarush.island.sharifullina.entity.organizm.animal.predator.Boa;
import com.javarush.island.sharifullina.entity.organizm.animal.predator.Eagle;
import com.javarush.island.sharifullina.entity.organizm.animal.predator.Wolf;
import com.javarush.island.sharifullina.repository.OrganizmFactory;
import com.javarush.island.sharifullina.services.ScheduleService;
import com.javarush.island.sharifullina.view.ConsoleView;

import java.util.stream.Stream;

import static java.util.Arrays.sort;
import static java.util.Arrays.stream;

public class GameRunner {
    public static void main(String[] args){
        Island island = new Island(20, 100);
        ConsoleView view = new ConsoleView(island);
        Game game = new Game(island, view);
        view.show();
        ScheduleService scheduleService = new ScheduleService(game);
        new Thread(scheduleService).start();
    }
}
