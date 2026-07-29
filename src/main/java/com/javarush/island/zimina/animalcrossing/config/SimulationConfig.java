package com.javarush.island.zimina.animalcrossing.config;


/**
 * Конфигурация параметров симуляции острова.
 * Содержит константы, определяющие размер острова и другие
 * настраиваемые параметры.
 */

public class SimulationConfig {
    /** Ширина острова в клетках. */
    public static final int ISLAND_WIDTH = 100;

    /** Высота острова в клетках. */
    public static final int ISLAND_HEIGHT = 20;

    /** Длительность одного такта симуляции в миллисекундах. */
    public static final long TICK_DURATION_MS = 1000;

}
