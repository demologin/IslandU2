package com.javarush.island.zelenin;

import com.javarush.island.zelenin.config.GameConfig;
import com.javarush.island.zelenin.core.GameEngine;
import com.javarush.island.zelenin.core.Initializer;
import com.javarush.island.zelenin.entity.map.Island;
import com.javarush.island.zelenin.service.*;
import com.javarush.island.zelenin.util.LifeCreator;
import com.javarush.island.zelenin.view.View;
import com.javarush.island.zelenin.view.console.ConsoleView;

public class ConsoleLauncher {

    public static void main(String[] args) {
        GameConfig config = new GameConfig();
        View view = new ConsoleView(config);
        LifeCreator creator = new LifeCreator(config);
        Initializer initializer = new Initializer(config, creator);
        Island island = initializer.initializeIsland();

        LifecycleService lifecycleService = new LifecycleService(
                new EatingService(config),
                new VitalityService(config),
                new ReproductionService(config, creator)
        );
        MovementService movementService = new MovementService(config, island);

        GameEngine engine = new GameEngine(config, island, view, lifecycleService, movementService);
        new Thread(engine).start();
    }
}
