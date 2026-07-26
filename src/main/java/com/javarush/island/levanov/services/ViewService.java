package com.javarush.island.levanov.services;

import com.javarush.island.levanov.entity.Game;

public class ViewService implements Runnable{
    private final Game game;

    public ViewService(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        game.getView().showStatistics();
//        game.getView().showMap();
        System.out.println();
    }
}
