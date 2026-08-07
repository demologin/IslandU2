package com.javarush.island.sharifullina;

import com.javarush.island.sharifullina.entity.map.Game;
import com.javarush.island.sharifullina.entity.map.Island;
import com.javarush.island.sharifullina.entity.organizm.Organizm;
import com.javarush.island.sharifullina.entity.organizm.Organizms;
import com.javarush.island.sharifullina.entity.organizm.Population;
import com.javarush.island.sharifullina.entity.organizm.animal.herbivore.Buffalo;
import com.javarush.island.sharifullina.entity.organizm.animal.herbivore.Goat;
import com.javarush.island.sharifullina.entity.organizm.animal.predator.Boa;
import com.javarush.island.sharifullina.entity.organizm.animal.predator.Eagle;
import com.javarush.island.sharifullina.entity.organizm.animal.predator.Wolf;
import com.javarush.island.sharifullina.repository.OrganizmFactory;
import com.javarush.island.sharifullina.services.ScheduleService;
import com.javarush.island.sharifullina.view.ConsoleView;

import java.util.stream.Stream;

import static java.util.Arrays.sort;
import static java.util.Arrays.stream;

public class GameRunner {
    public static void main(String[] args) throws Exception {
//        Cell cell = new Cell("Wolf");
//        Organizm organizm = new Wolf(210);
//        cell.safeAddOrganizm(organizm);
//        for (int i = 0; i<100; i++) {
//            organizm.changeWeight(cell, 50);
//            System.out.println(cell.toString());
//        }

        Island island = new Island(2, 2);

//        System.out.println(island.getCell(0,0).toString());
//        island.getCell(0,0).getTopInPercent().entrySet().stream().forEach(System.out::println);
        ConsoleView view = new ConsoleView(island);
        Game game = new Game(island, view);
        view.show();

        ScheduleService scheduleService = new ScheduleService(game);
        new Thread(scheduleService).start();
//
//        view.show();
//
//        Cell cell = island.getCell(0, 0);
//        Organizm organizm = cell.getPopulation("Sheep").getOrganizms().stream().findAny().get();
//        if (organizm != null && organizm instanceof Animal animal ) {
//            System.out.println(animal.eat(cell));
//        }
//
//        for (String type : new String[]{"Wolf", "Sheep"}) {
//            Map<Cell, TypePopulation> organizms = game.getIsland().getOrganizmsByType(type);
//            organizms
//                    .entrySet()
//                    .stream()
//                    .forEach(
//                            entry -> {
//                                entry.getValue()
//                                        .getOrganizms()
//                                        .stream()
//                                        .map(organizm -> (Animal) organizm)
//                                        .forEach(animal -> {
//                                            Cell cell = entry.getKey();
////                                            animal.loseWeight(cell);
//                                            animal.move(cell);
////                                            animal.eat(cell);
////                                            animal.safeReproduce(cell);
//                                        });
//                            });
//        }
//        view.show();


//        view.show();
//        System.out.println("*".repeat(25));
//        island.getStatistics().entrySet().stream().forEach(o -> System.out.print(o.getKey() + " : " + o.getValue() + " ".repeat(3)));
//        System.out.println();
//        Arrays.stream(island.getCells()).flatMap(Arrays::stream).map(cell -> cell.getTopInPercent()).forEach(System.out::println);
//        Arrays.stream(island.getCells()).flatMap(Arrays::stream).forEach(System.out::println);
//        System.out.println(island.getCell(0, 0).getResidence().toString());

    }
}
