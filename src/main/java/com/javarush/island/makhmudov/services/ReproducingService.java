package com.javarush.island.makhmudov.services;

import com.javarush.island.makhmudov.entity.Game;

import java.util.Arrays;

public class ReproducingService extends AbstractService {

    public ReproducingService(Game game) {
        super(game);
    }

    @Override
    public void run() {
        Arrays.stream(game.getGameMap().getCells()) //row
                .flatMap(Arrays::stream) //cell
                .forEach(cell -> processOneCell(cell, o -> o.spawn(cell)));
    }
}
