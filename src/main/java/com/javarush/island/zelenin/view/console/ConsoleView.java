package com.javarush.island.zelenin.view.console;

import com.javarush.island.zelenin.config.GameConfig;
import com.javarush.island.zelenin.entity.organism.OrganismType;
import com.javarush.island.zelenin.repository.GameStats;
import com.javarush.island.zelenin.view.View;
import com.javarush.island.zelenin.view.console.style.Color;
import com.javarush.island.zelenin.view.console.style.Layout;

public class ConsoleView implements View {
    private final GameConfig config;
    private final ReportBuffer reportBuffer;

    public ConsoleView(GameConfig config) {
        this.config = config;
        reportBuffer = new ReportBuffer();
    }

    @Override
    public void render(int currentTick) {
        showStats(currentTick);
    }

    private void showStats(int currentTick) {
        reportBuffer.update(GameStats.snapshot());
        StringBuilder report = new StringBuilder();
        report.append(renderReportHeader(currentTick));

        for (OrganismType type : OrganismType.values()) {
            report.append(renderSpeciesRow(type));
        }
        report.append(Layout.LINE);
        String totalBar = renderTotalBar(
                reportBuffer.getCarnivores(),
                reportBuffer.getHerbivores(),
                reportBuffer.getPlants()
        );
        report.append(Layout.TOTAL).append(totalBar).append("\n");
        report.append(Layout.CYAN_LINE);
        System.out.print(report);
    }

    private String renderSpeciesRow(OrganismType type) {
        long trend = reportBuffer.getTrend(type);
        long count = reportBuffer.getCount(type);
        long maxCount = config.getTotalMaxCount(type);

        return Layout.SPECIES_FORMAT.formatted(
                config.getIcon(type),
                renderColoredName(type),
                count,
                renderSmallBar(count, maxCount),
                renderTrendInfo(trend)
        );
    }

    private String renderReportHeader(int currentTick) {
        return "\n" + Layout.CYAN_LINE + Layout.ISLAND.formatted(currentTick) + Layout.CYAN_LINE;
    }

    private String renderColoredName(OrganismType type) {
        String name = config.getName(type);
        if (type.isCarnivore()) {
            name = Color.RED + name;
        } else if (type.isHerbivore()) {
            name = Color.YELLOW + name;
        } else {
            name = Color.GREEN + name;
        }
        return name + Color.RESET;
    }

    private String renderTrendInfo(long delta) {
        if (delta > 0) {
            return Layout.TREND_UP.formatted(delta);
        } else if (delta < 0) {
            return Layout.TREND_DOWN.formatted(delta);
        }
        return Layout.TREND_NONE;
    }

    private String renderSmallBar(long count, long maxCount) {
        int barWidth = Layout.SMALL_BAR_WIDTH;
        double ratio = (double) count / maxCount;
        int filledWidth = (int) (ratio * barWidth);
        int emptyWidth = barWidth - filledWidth;
        String color = ratio < 0.20 ? Color.RED : Color.GREEN;
        return color + "█".repeat(filledWidth) + "░".repeat(emptyWidth) + Color.RESET;
    }

    private String renderTotalBar(long carnivores, long herbivores, long plants) {
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
