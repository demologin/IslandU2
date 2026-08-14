package com.javarush.island.sharifullina.services;

import com.javarush.island.sharifullina.entity.map.Game;

public class ViewService implements Runnable{
    private final Game game;
    public ViewService(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        game.getView().show();
    }
}
