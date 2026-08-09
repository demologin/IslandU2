package com.javarush.island.tikhonova.services;

import com.javarush.island.tikhonova.WriteInFile;
import com.javarush.island.tikhonova.entity.Game;
import com.javarush.island.tikhonova.entity.map.CellMap;
import com.javarush.island.tikhonova.entity.map.GameMap;
import com.javarush.island.tikhonova.entity.map.Map;
import com.javarush.island.tikhonova.entity.organizm.Organizm;
import com.javarush.island.tikhonova.entity.organizm.animals.herbivores.*;
import com.javarush.island.tikhonova.entity.organizm.animals.predators.*;
import com.javarush.island.tikhonova.entity.organizm.plants.Grass;

import java.io.FileWriter;
import java.io.IOException;

public class ReproductionService extends AbstractService {
    public ReproductionService(Game game) {
        super(game);
    }

    private static String ADDRESS_FOR_LOG = "src/main/java/com/javarush/island/tikhonova/logReproduction.txt";
    public static FileWriter fileWriter;

    static {
        try {
            fileWriter = new FileWriter(ADDRESS_FOR_LOG);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void reproductionService(CellMap cell) {
        for (int i = 0; i < Map.nameOrganizms.length; i++) {
            String name = Map.nameOrganizms[i];
            if (!cell.getOrganizmsInSectionWithKey().containsKey(name)
                    || cell.getOrganizmsInSectionWithKey().get(name).getCountOrganizms() < 1
                    || cell.getOrganizmsInSectionWithKey().get(name)
                    .getWeight() < cell.getOrganizmsInSectionWithKey().get(name).getLimit().getMinWeight()) {
                continue;
            }
            cell.getOrganizmsInSectionWithKey().get(name).reproduction(cell, name);
            try {
                fileWriter.write(name + " родил: " + "; ");//+ cell.getOrganizmsInSectionWithKey().get(name).getWeight() +
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }

    @Override
    public void run() {
        game.getGameMap().getStreamCells().forEach(this::reproductionService);
    }
}
