package com.javarush.island.makhmudov;

import com.javarush.island.makhmudov.services.GameServiceProcessor;
import com.javarush.island.makhmudov.services.*;
import com.javarush.island.makhmudov.repository.EntityCreator;
import com.javarush.island.makhmudov.repository.GameMapCreator;
import com.javarush.island.makhmudov.view.console.ConsoleView;
import com.javarush.island.makhmudov.api.init.Initialization;
import com.javarush.island.makhmudov.api.view.View;
import com.javarush.island.makhmudov.config.Setting;
import com.javarush.island.makhmudov.entity.Game;
import com.javarush.island.makhmudov.entity.map.GameMap;

import java.util.List;

public class IslandRunner {
    public static void main(String[] args) {
        Initialization entityFactory = new EntityCreator();
        GameMapCreator gameMapCreator = new GameMapCreator(entityFactory);
        int rows = Setting.get().life.getRows();
        int cols = Setting.get().life.getCols();
        GameMap gameMap = gameMapCreator.createRandomFilledGameMap(rows, cols, 1);
        View view = new ConsoleView(gameMap);
        Game game = new Game(gameMap, entityFactory, view);
        List<Runnable> services = List.of(
                new EatingService(game),
                new MovingService(game),
                new ReproducingService(game),
                new RandomFillService(game),
                new ViewService(game)
        );
        GameServiceProcessor gameWorkerService = new GameServiceProcessor(game, services);
        gameWorkerService.start();
    }
}
