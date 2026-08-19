package com.javarush.island.makhmudov.entity;

import com.javarush.island.makhmudov.api.init.Initialization;
import com.javarush.island.makhmudov.api.view.View;
import com.javarush.island.makhmudov.entity.map.GameMap;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Game {

    private final GameMap gameMap;
    private final Initialization entityFactory;
    private View view;
    private boolean isFinished = false;

    public Game(GameMap gameMap, Initialization entityFactory, View view) {
        this.gameMap = gameMap;
        this.entityFactory = entityFactory;
        this.view = view;
    }
}
