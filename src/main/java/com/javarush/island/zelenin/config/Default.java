package com.javarush.island.zelenin.config;

import com.javarush.island.zelenin.config.GameConfig.OrganismProps;
import com.javarush.island.zelenin.entity.organism.OrganismType;

import java.util.Map;

import static com.javarush.island.zelenin.entity.organism.OrganismType.*;
import static java.util.Map.entry;


//TODO abstract final, private constructor?
public class Default {
    public static final int ROWS = 100;
    public static final int COLS = 100;

    public static final Map<OrganismType, OrganismProps> ORGANISMS = Map.ofEntries(
            entry(WOLF,    new OrganismProps("Wolf",    "\uD83D\uDC3A",  50,      30, 3,   8,      2,      25,  0)),
            entry(SNAKE,   new OrganismProps("Snake",   "\uD83D\uDC0D",  15,      30, 1,   3,      0.5,    25,  0)),
            entry(FOX,     new OrganismProps("Fox",     "\uD83E\uDD8A",   8,      30, 2,   2,      0.5,    25,  0)),
            entry(BEAR,    new OrganismProps("Bear",    "\uD83D\uDC3B", 500,       5, 2,  80,     10,      25,  0)),
            entry(EAGLE,   new OrganismProps("Eagle",   "\uD83E\uDD85",   6,      20, 3,   1,      0.2,    25,  0)),
            entry(HORSE,   new OrganismProps("Horse",   "\uD83D\uDC0E", 400,      20, 4,  60,     10,      25,  0)),
            entry(DEER,    new OrganismProps("Deer",    "\uD83E\uDD8C", 300,      20, 4,  50,     10,      25,  0)),
            entry(RABBIT,  new OrganismProps("Rabbit",  "\uD83D\uDC07",   2,     150, 2,   0.5,    0.125,  50,  0)),
            entry(MOUSE,   new OrganismProps("Mouse",   "\uD83D\uDC01",   0.05,  500, 1,   0.01,   0.0025, 50,  0)),
            entry(GOAT,    new OrganismProps("Goat",    "\uD83D\uDC10",  60,     140, 3,  10,      2,      25,  0)),
            entry(SHEEP,   new OrganismProps("Sheep",   "\uD83D\uDC11",  70,     140, 3,  15,      3,      25,  0)),
            entry(BOAR,    new OrganismProps("Boar",    "\uD83D\uDC17", 400,      50, 2,  50,     10,      25,  0)),
            entry(BUFFALO, new OrganismProps("Buffalo", "\uD83D\uDC03", 700,      10, 3, 100,     20,      25,  0)),
            entry(DUCK,    new OrganismProps("Duck",    "\uD83E\uDD86",   1,     200, 4,   0.15,   0.03,   50,  0)),
            entry(WORM,    new OrganismProps("Worm",    "\uD83D\uDC1B",   0.01, 1000, 0,   0.005,  0.001,  50,  0)),
            entry(GRASS,   new OrganismProps("Grass",   "\uD83C\uDF31",   1,     200, 0,   0,      0,       0, 10))
    );

    public static final Map<OrganismType, Map<OrganismType, Integer>> EATING_MATRIX = Map.ofEntries(
            entry(WOLF,    Map.of(HORSE, 10, DEER, 15, RABBIT, 60, MOUSE, 80, GOAT, 60, SHEEP, 70, BOAR, 15, BUFFALO, 10, DUCK, 50)),
            entry(SNAKE,   Map.of(FOX, 15, RABBIT, 20, MOUSE, 40, DUCK, 10)),
            entry(FOX,     Map.of(RABBIT, 70, MOUSE, 90, DUCK, 60, WORM, 40)),
            entry(BEAR,    Map.of(SNAKE, 80, HORSE, 40, DEER, 80, RABBIT, 80, MOUSE, 90, GOAT, 70, SHEEP, 70, BOAR, 50, BUFFALO, 20, DUCK, 10)),
            entry(EAGLE,   Map.of(FOX, 10, RABBIT, 90, MOUSE, 90, DUCK, 80)),
            entry(HORSE,   Map.of(GRASS, 100)),
            entry(DEER,    Map.of(GRASS, 100)),
            entry(RABBIT,  Map.of(GRASS, 100)),
            entry(MOUSE,   Map.of(WORM, 90, GRASS, 100)),
            entry(GOAT,    Map.of(GRASS, 100)),
            entry(SHEEP,   Map.of(GRASS, 100)),
            entry(BOAR,    Map.of(MOUSE, 50, WORM, 90, GRASS, 100)),
            entry(BUFFALO, Map.of(GRASS, 100)),
            entry(DUCK,    Map.of(WORM, 90, GRASS, 100)),
            entry(WORM,    Map.of(GRASS, 100))
    );
}
