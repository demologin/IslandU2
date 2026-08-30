package com.javarush.island.zelenin.view.console.style;

import com.javarush.island.zelenin.util.Const;

public final class Layout {
    public static final String CYAN_LINE = Color.CYAN + "=".repeat(50) + Color.RESET + "\n";
    public static final String LINE = "-".repeat(50) + "\n";
    public static final String ISLAND = Color.CYAN + "\uD83C\uDF34 ISLAND LIFE: Day %d" + "\n";

    public static final String TREND_UP = "%s▲ %%+7d%s".formatted(Color.GREEN, Color.RESET);
    public static final String TREND_DOWN = "%s▼ %%7d%s".formatted(Color.RED, Color.RESET);
    public static final String TREND_NONE = "%s▪       0%s".formatted(Color.YELLOW, Color.RESET);
    public static final String ALL_DEAD = "[ %sALL DEAD%s ]".formatted(Color.RED, Color.RESET);

    public static final String SPECIES_FORMAT = "%s %-16s : %9d %s  %s\n";
    public static final String TOTAL = "Biosphere balance: ";

    public static final int SMALL_BAR_WIDTH = 15;
    public static final int TOTAL_BAR_WIDTH = 30;

    private Layout() {
        throw new UnsupportedOperationException(Const.UNSUPPORTED_OPERATION);
    }
}
