package com.javarush.island.rukavanov.renderer;

import java.util.Map;

public interface Renderer {
    void render(long tick, Map<String, Integer> statistics);
}
