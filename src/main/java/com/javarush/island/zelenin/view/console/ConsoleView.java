package com.javarush.island.zelenin.view.console;

import com.javarush.island.zelenin.config.GameConfig;
import com.javarush.island.zelenin.entity.organism.OrganismType;
import com.javarush.island.zelenin.util.GlobalStats;
import com.javarush.island.zelenin.view.View;
import com.javarush.island.zelenin.view.console.util.Color;
import com.javarush.island.zelenin.view.console.util.Layout;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConsoleView implements View {
    private final GameConfig config;
    private final Map<OrganismType, Long> lastSnapshot;
    private final Map<OrganismType, Long> maxCounts;

    public ConsoleView(GameConfig config) {
        this.config = config;
        lastSnapshot = new ConcurrentHashMap<>();
        maxCounts = new HashMap<>();
        for (OrganismType type : OrganismType.values()) {
            maxCounts.put(type, (long) config.getMaxCount(type)
                    * config.getRows() * config.getCols());
        }
    }

    @Override
    public void render(int currentTick) {
        showStats(currentTick);
    }

    private void showStats(int currentTick) {
        GlobalStats.resetTotals();
        Map<OrganismType, Long> snapshot = GlobalStats.snapshot();
        StringBuilder report = new StringBuilder();
        report.append(getName(currentTick));

        for (OrganismType type : OrganismType.values()) {
            long count = snapshot.getOrDefault(type, 0L);
            long lastCount = lastSnapshot.getOrDefault(type, count);
            long maxCount = maxCounts.get(type);

            GlobalStats.updateTotals(type, count);
            String speciesBar = getSmallBar(count, maxCount);
            String trendArrow = getTrendArrow(count - lastCount);
            report.append(Layout.SPECIES_FORMAT.formatted(config.getIcon(type),
                    config.getName(type), count, speciesBar, trendArrow));
        }

        report.append(Layout.LINE);
        String totalBar = getTotalBar(GlobalStats.getTotalCarnivores(),
                GlobalStats.getTotalHerbivores(), GlobalStats.getTotalPlants());
        report.append(Layout.TOTAL).append(totalBar).append("\n");
        report.append(Layout.CYAN_LINE);

        System.out.print(report);
        lastSnapshot.clear();
        lastSnapshot.putAll(snapshot);
    }

    private String getName(int currentTick) {
        return "\n" + Layout.CYAN_LINE + Layout.ISLAND.formatted(currentTick) + Layout.CYAN_LINE;
    }

    private String getTrendArrow(long delta) {
        if (delta > 0) {
            return Layout.TREND_UP.formatted(delta);
        } else if (delta < 0) {
            return Layout.TREND_DOWN.formatted(delta);
        }
        return Layout.TREND_NONE;
    }

    private String getSmallBar(long count, long maxCount) {
        int barWidth = Layout.SMALL_BAR_WIDTH;
        double ratio = (double) count / maxCount;
        int filledWidth = (int) (ratio * barWidth);
        int emptyWidth = barWidth - filledWidth;
        String color = ratio < 0.20 || ratio > 0.80 ? Color.RED : Color.GREEN;
        return color + "█".repeat(filledWidth) + "░".repeat(emptyWidth) + Color.RESET;
    }

    private String getTotalBar(long carnivores, long herbivores, long plants) {
        long total = carnivores + herbivores + plants;
        if (total == 0) return Layout.ALL_DEAD;

        int barWidth = Layout.TOTAL_BAR_WIDTH;
        int carnivoreWidth = (int) Math.round((double) carnivores / total * barWidth);
        int herbivoreWidth = (int) Math.round((double) herbivores / total * barWidth);
        int plantWidth = barWidth - carnivoreWidth - herbivoreWidth;

        return Color.BG_RED + " ".repeat(carnivoreWidth)
                + Color.BG_YELLOW + " ".repeat(herbivoreWidth)
                + Color.BG_GREEN  + " ".repeat(plantWidth) + Color.RESET;
    }
}
