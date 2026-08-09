package com.javarush.island.tikhonova.services;

import com.javarush.island.tikhonova.entity.Game;
import com.javarush.island.tikhonova.entity.Initialization;
import com.javarush.island.tikhonova.entity.map.CellMap;
import com.javarush.island.tikhonova.entity.map.GameMap;
import com.javarush.island.tikhonova.entity.map.Map;
import com.javarush.island.tikhonova.util.Rand;

public class RandomFillService extends AbstractService {
    public static final double PERCENT_PROBABLY = 1;
    private final int ROWS;
    private final int COLS;
    //private final Initialization entityFactory;

    public RandomFillService(Game game) {
        super(game);
        ROWS = Map.HEIGHT_MAP;
        COLS = Map.WIDTH_MAP;
        //entityFactory = game.getEntityFactory();
    }

    @Override
    public void run() {
        int row = Rand.random(0, ROWS);
        int col = Rand.random(0, COLS);
        CellMap cellMap = game.getGameMap().getCellMap(row, col);
        //safeFill(cellMap);
    }

//    private void safeFill(CellMap cellMap){
//        synchronized (cellMap.monitor()){
//            entityFactory.fill(cellMap, PERCENT_PROBABLY);
//        }
//    }
}
