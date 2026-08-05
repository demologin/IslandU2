package com.javarush.island.rukavanov.renderer;

import java.util.Map;

public class ConsoleRenderer implements Renderer {

    @Override
    public void render(long tick, Map<String, Integer> statistics) {

        System.out.println();
        System.out.println("========== Tick " + tick + " ==========");
        System.out.println(statistics);

    }
}
