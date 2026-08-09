package com.javarush.island.tikhonova.entity.map;

public class StatisticOrganizm {

    public static volatile int[] countOrganizmStatistic = new int[Map.nameOrganizms.length];
    public static String[] symbols = new String[]{"\uD83D\uDC03", "\uD83D\uDC1B", "\uD83E\uDD8C",
            "\uD83E\uDD86", "\uD83D\uDC10", "\uD83D\uDC0E", "\uD83D\uDC01", "\uD83D\uDC07",
            "\uD83D\uDC11", "\uD83D\uDC17", "\uD83D\uDC3B", "\uD83D\uDC0D", "\uD83E\uDD85",
            "\uD83E\uDD8A", "\uD83D\uDC3A", "\uD83C\uDF3F"};

    private StatisticOrganizm() {
    }


}
