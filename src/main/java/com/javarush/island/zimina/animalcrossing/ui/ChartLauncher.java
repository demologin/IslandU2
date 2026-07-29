package com.javarush.island.zimina.animalcrossing.ui;

/**
 * Точка входа для запуска JavaFX-приложения со статистикой острова.
 * Не наследуется от Application намеренно — это позволяет запускать
 * приложение напрямую (в т.ч. из IDEA) без module-path ошибок.
 */
public class ChartLauncher {
    public static void main(String[] args) {
        IslandChartApp.main(args);
    }
}