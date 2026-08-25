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
        LifeCreator creator = new LifeCreator(config);
        Initializer initializer = new Initializer(config, creator);
        Island island = initializer.initializeMap();

        EatingService eatingService = new EatingService(config);
        VitalityService vitalityService = new VitalityService(config);
        ReproductionService reproductionService = new ReproductionService(config, creator);
        LifecycleService lifecycleService = new LifecycleService(eatingService, vitalityService, reproductionService);
        MovementService movementService = new MovementService(island, config);

        View view = new ConsoleView(config);
        GameEngine engine = new GameEngine(island, lifecycleService, movementService, view);
        new Thread(engine).start();
    }
}
