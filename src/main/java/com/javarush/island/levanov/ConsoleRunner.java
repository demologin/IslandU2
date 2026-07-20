package com.javarush.island.levanov;

import com.javarush.island.levanov.entity.Creator;
import com.javarush.island.levanov.entity.EntityFactory;
import com.javarush.island.levanov.entity.Game;
import com.javarush.island.levanov.entity.map.Cell;
import com.javarush.island.levanov.entity.map.GameMap;
import com.javarush.island.levanov.entity.organisms.Organism;
import com.javarush.island.levanov.entity.organisms.animals.Animal;
import com.javarush.island.levanov.entity.organisms.animals.predators.Wolf;
import com.javarush.island.levanov.services.GameServiceProcessor;
import com.javarush.island.levanov.services.MovingService;
import com.javarush.island.levanov.services.ViewService;
import com.javarush.island.levanov.util.Randomizer;
import com.javarush.island.levanov.view.ConsoleView;
import org.w3c.dom.ls.LSOutput;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConsoleRunner {
    public static void main(String[] args) {
        EntityFactory entityFactory = new EntityFactory();
        Creator creator = new Creator(entityFactory);
        GameMap gameMap = creator.createGameMap();
        ConsoleView console = new ConsoleView(gameMap);
        Game game = new Game(gameMap, console);
        List<Runnable> services = List.of(
                new MovingService(game),
                new ViewService(game)
        );

        GameServiceProcessor gameServiceProcessor = new GameServiceProcessor(game, services);
        Thread thread = new Thread(gameServiceProcessor);
        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        game.setFinished(true);
        System.out.println();
        console.showStatistics();

    }
}
