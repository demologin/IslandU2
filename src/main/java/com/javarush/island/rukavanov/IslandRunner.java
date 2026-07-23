package com.javarush.island.rukavanov;

import com.javarush.island.rukavanov.entity.island.Island;
import com.javarush.island.rukavanov.entity.island.IslandSnapshot;
import com.javarush.island.rukavanov.services.InitializationService;
import com.javarush.island.rukavanov.services.StatisticsCollector;
import com.javarush.island.rukavanov.view.ConsoleView;
import com.javarush.island.rukavanov.view.IslandView;

public class IslandRunner {
    public static void main(String[] args) {

        Island island = new Island();

        InitializationService initializationService = new InitializationService();
        initializationService.initialize(island);
        StatisticsCollector collector = new StatisticsCollector();

        IslandSnapshot snapshot = collector.collect(island, 1);
        IslandView view = new ConsoleView();

        view.render(snapshot);

    }
}
