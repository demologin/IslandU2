package com.javarush.island.rukavanov.view;

import com.javarush.island.rukavanov.config.OrganismType;
import com.javarush.island.rukavanov.entity.island.IslandSnapshot;

public class ConsoleView implements IslandView {

    @Override
    public void render(IslandSnapshot snapshot) {

        System.out.println();
        System.out.println("========== ТАКТ " + snapshot.getTick() + " ==========");
        System.out.print("{");

        boolean first = true;

        for (OrganismType type : OrganismType.values()) {
            if (!first) {
                System.out.print(", ");
            }
            System.out.print(type.getIcon() + "=" + snapshot.getStatistics().get(type)
            );
            first = false;
        }
        System.out.println("}");
    }
}
