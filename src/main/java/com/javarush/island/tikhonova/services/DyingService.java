package com.javarush.island.tikhonova.services;

import com.javarush.island.tikhonova.WriteInFile;
import com.javarush.island.tikhonova.entity.Game;
import com.javarush.island.tikhonova.entity.map.CellMap;
import com.javarush.island.tikhonova.entity.map.GameMap;
import com.javarush.island.tikhonova.entity.map.Map;
import com.javarush.island.tikhonova.entity.map.StatisticOrganizm;
import com.javarush.island.tikhonova.entity.organizm.Organizm;
import com.javarush.island.tikhonova.entity.organizm.animals.herbivores.*;
import com.javarush.island.tikhonova.entity.organizm.animals.predators.*;
import com.javarush.island.tikhonova.entity.organizm.plants.Grass;
import com.sun.jdi.request.StepRequest;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class DyingService extends AbstractService {
    public DyingService(Game game) {
        super(game);
    }

    private static String ADDRESS_FOR_LOG = "src/main/java/com/javarush/island/tikhonova/logDying.txt";
    public static FileWriter fileWriter;

    static {
        try {
            fileWriter = new FileWriter(ADDRESS_FOR_LOG);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void dyingService(CellMap cell) {
        for (int i = 0; i < Map.nameOrganizms.length; i++) {


            String name = Map.nameOrganizms[i];

            if (!cell.getOrganizmsInSectionWithKey().containsKey(name)
                    || cell.getOrganizmsInSectionWithKey().get(name).getCountOrganizms() < 1
                    || cell.getOrganizmsInSectionWithKey().get(name)
                    .getWeight() < cell.getOrganizmsInSectionWithKey().get(name).getLimit().getMinWeight()) {
                continue;
            }

            boolean deadOrLive = cell.getOrganizmsInSectionWithKey().get(name).dying(cell, name);

            String weight = String.valueOf(cell.getOrganizmsInSectionWithKey().get(name).getWeight());

            if (deadOrLive == true) {

                int count = cell.getOrganizmsInSectionWithKey().get(name).getCountOrganizms();


                synchronized (StatisticOrganizm.class) {
                    StatisticOrganizm.countOrganizmStatistic[i] = StatisticOrganizm.countOrganizmStatistic[i] - count;
                }
                cell.deleteOrganizm(name);

                try {
                    fileWriter.write("x= " + cell.getIndexX() + " y= " + cell.getIndexY() + name + " отправился к звездам с весом: " + weight + "; " + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }

        }

    }


    @Override
    public void run() {
        game.getGameMap().getStreamCells().forEach(this::dyingService);
    }
}
