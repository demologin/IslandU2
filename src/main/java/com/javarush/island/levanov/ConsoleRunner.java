package com.javarush.island.levanov;

import com.javarush.island.levanov.entity.Creator;
import com.javarush.island.levanov.entity.EntityFactory;
import com.javarush.island.levanov.entity.Game;
import com.javarush.island.levanov.entity.map.GameMap;
import com.javarush.island.levanov.services.*;
import com.javarush.island.levanov.view.ConsoleView;

import java.util.List;

public class ConsoleRunner {
    public static void main(String[] args) {
        EntityFactory entityFactory = new EntityFactory();
        Creator creator = new Creator(entityFactory);
        GameMap gameMap = creator.createGameMap();
        ConsoleView console = new ConsoleView(gameMap);
        Game game = new Game(gameMap,entityFactory, console);
        // проверить все поля на private/public
        // добавить функциональные интерфейсы?
        // проверить Override
        // проверить названия методов и переменных
        // постараться сделать читаемым
        // проверить что не смешиваем for и stream
        List<Runnable> services = List.of(
                new MovingService(game),
                new EatingService(game),
                new PlantGrowingService(game),
                new LooseWeightService(game),
                new ReproduceService(game),
                new ViewService(game)
        );

        GameServiceProcessor gameServiceProcessor = new GameServiceProcessor(game, services);
        Thread thread = new Thread(gameServiceProcessor);
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        game.setFinished(true);
    }
}
