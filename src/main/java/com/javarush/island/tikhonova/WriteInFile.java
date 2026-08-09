package com.javarush.island.tikhonova;

import java.io.FileWriter;
import java.io.IOException;

public class WriteInFile {

    private static String ADDRESS_FOR_LOG = "src/main/java/com/javarush/island/tikhonova/log.txt";
    public static FileWriter fileWriter;

    static {
        try {
            fileWriter = new FileWriter(ADDRESS_FOR_LOG);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void write(String str) {
        try {
            fileWriter.write(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeWriter() throws IOException {
        fileWriter.close();
    }


}
