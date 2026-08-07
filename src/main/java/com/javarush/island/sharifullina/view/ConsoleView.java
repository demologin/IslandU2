package com.javarush.island.sharifullina.view;

import com.javarush.island.sharifullina.entity.organizm.Settings;
import com.javarush.island.sharifullina.entity.map.Cell;
import com.javarush.island.sharifullina.entity.map.Island;

import java.util.Map;

public class ConsoleView implements View {
    private final Island island;
    private final int column;
    private final int row;
    private final String border;
    boolean isAllColShow;
    boolean isAllRowShow;

    public ConsoleView(Island island) {
        this.island = island;
        isAllColShow = island.getColumn() <= Settings.COLUMN;
        isAllRowShow = island.getRow() <= Settings.ROW;
        column = Math.min(island.getColumn(), Settings.COLUMN);
        row = Math.min(island.getRow(), Settings.ROW);
        border = "═".repeat(Settings.TOP_ORGANIZM_COUNT);
        isAllColShow = island.getColumn() <= Settings.COLUMN;
        isAllRowShow = island.getRow() <= Settings.ROW;
    }

    @Override
    public void show() {
        showIslandStatistic();
        showScale();
        showCellsStatistic();

//        Arrays.stream(island.getCells()).flatMap(Arrays::stream).forEach(System.out::println);
//        System.out.println();
//        System.out.println(island.getCommonWeight());
        System.out.println("#################################");
    }

    private void showCellsStatistic() {
        StringBuilder sb = new StringBuilder();
        Cell[][] cells = island.getCells();
        for (int i = 0; i < row; i++) {
            sb.append(delimetrLine(i)).append(System.lineSeparator());
            for (int j = 0; j < column; j++) {
                sb.append(Boarder.CELL_MARGIN);
                Map<String, Integer> topInPercent = cells[i][j].getTopInPercent();
                topInPercent.forEach(
                        (k, v) -> {
                            String color = Color.getColor(v);
                            String str = color + safeGetLetter(k, 1) + Color.RESET;
                            sb.append(str);
                        });
                if (topInPercent.size() < Settings.TOP_ORGANIZM_COUNT) {
                    sb.append(".".repeat(Settings.TOP_ORGANIZM_COUNT - topInPercent.size()));
                }
            }
            sb.append(!isAllColShow ? Boarder.INF_MARGIN : Boarder.CELL_MARGIN).append(System.lineSeparator());
        }
        sb.append(delimetrLine(row)).append(System.lineSeparator());
        System.out.println(sb);
    }

    private String safeGetLetter(String word, int n){
        if(word == null || word.isEmpty()){
            return "?";
        }
        if(word.length() == n){
            return word;
        }
        return word.substring(0, n);
    }

    private void showIslandStatistic() {
        island.getStatistics().entrySet().forEach(o -> System.out.print(o.getKey() + " : " + o.getValue() + "  "));
        System.out.println();
    }

    private void showScale() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            String color = Color.getColor(i * 10);
            String str = color + i * 10 + "-" + (i + 1) * 10 + Color.RESET;
            sb.append(str).append(" ");
        }
        sb.append(Color.RED + 100 + Color.RESET + " ");
        sb.append(Color.FILL_RED + "> 100" + Color.RESET);
        System.out.println(sb);
    }

    private String delimetrLine(int row) {
        if (row == 0) {
            return topLine();
        }
        if (row == this.row) {
            return bottomLine();
        }
        return centerLine();
    }

    private String topLine() {
        StringBuilder sb = new StringBuilder();
        sb.append(Boarder.LEFT_TOP + border);
        for (int i = 0; i < column - 1; i++) {
            sb.append(Boarder.TOP + border);
        }
        if (!isAllColShow) {
            sb.append(Boarder.INF_MARGIN);
        } else {
            sb.append(Boarder.RIGHT_TOP);
        }
        return sb.toString();
    }

    private String bottomLine() {
        if (!isAllRowShow) {
            return ("" + Boarder.INF_MARGIN).repeat(Settings.TOP_ORGANIZM_COUNT + 1).repeat(column);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Boarder.LEFT_BOTTOM + border);
        for (int i = 0; i < column - 1; i++) {
            sb.append(Boarder.CENTER_BOTTOM + border);
        }
        if (!isAllColShow) {
            sb.append(Boarder.INF_MARGIN);
        } else {
            sb.append(Boarder.RIGHT_BOTTOM);
        }
        return sb.toString();
    }

    private String centerLine() {
        StringBuilder sb = new StringBuilder();
        sb.append(Boarder.LEFT + border);
        for (int i = 0; i < column - 1; i++) {
            sb.append(Boarder.CENTER + border);
        }
        if (!isAllColShow) {
            sb.append(Boarder.INF_MARGIN);
        } else {
            sb.append(Boarder.RIGHT);
        }
        return sb.toString();
    }
}
