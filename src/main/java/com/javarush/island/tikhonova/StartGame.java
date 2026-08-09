package com.javarush.island.tikhonova;

import com.javarush.island.tikhonova.view.Programm;

import java.io.IOException;

public class StartGame {
    public static void main(String[] args) throws IOException {
        Programm programm = new Programm();
        programm.printOrganizm();
    }
}
