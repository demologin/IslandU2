package com.javarush.island.sharifullina.entity.map;

import com.javarush.island.sharifullina.view.View;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Game {
    private Island island;
    private View view;
    private boolean gameOver =  false;

    public Game(Island island, View view) {
        this.island = island;
        this.view = view;
    }

    public boolean isGameOver() {
        Integer count = island.getStatistics().values().stream().reduce((a, b) -> a + b).get();
        return count == 0;
    }
}
