package com.javarush.island.rukavanov.renderer;

import java.util.Map;

public class ConsoleRenderer implements Renderer {

    @Override
    public void render(Map<String, Integer> statistics) {

        System.out.println(statistics);

    }
}
