package com.javarush.island.zimina.animalcrossing.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.javarush.island.zimina.animalcrossing.config.SimulationConfig;
import com.javarush.island.zimina.animalcrossing.engine.SimulationTick;
import com.javarush.island.zimina.animalcrossing.engine.StatisticsService;
import com.javarush.island.zimina.animalcrossing.map.Island;
import com.javarush.island.zimina.animalcrossing.simulation.IslandPopulator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class IslandChartApp extends Application {

    private static final int MAX_TICKS = 100;

    private static final Map<String, String> ICONS = new LinkedHashMap<>();
    static {
        ICONS.put("Wolf", "🐺 Wolf");
        ICONS.put("Bear", "🐻 Bear");
        ICONS.put("Fox", "🦊 Fox");
        ICONS.put("Boa", "🐍 Boa");
        ICONS.put("Eagle", "🦅 Eagle");
        ICONS.put("Horse", "🐴 Horse");
        ICONS.put("Deer", "🦌 Deer");
        ICONS.put("Rabbit", "🐰 Rabbit");
        ICONS.put("Goat", "🐐 Goat");
        ICONS.put("Sheep", "🐑 Sheep");
        ICONS.put("Buffalo", "🐃 Buffalo");
        ICONS.put("Mouse", "🐭 Mouse");
        ICONS.put("Boar", "🐗 Boar");
        ICONS.put("Duck", "🦆 Duck");
        ICONS.put("Caterpillar", "🐛 Caterpillar");
        ICONS.put("Plant", "🌱 Plant");
    }

    private static final Set<String> PREDATORS = Set.of("Wolf", "Bear", "Fox", "Boa", "Eagle");
    private static final Set<String> OMNIVORES = Set.of("Mouse", "Boar", "Duck");

    private Island island;
    private SimulationTick simulationTick;
    private StatisticsService statistics;
    private BarChart<String, Number> chart;
    private XYChart.Series<String, Number> series;
    private Label dayLabel;
    private AtomicInteger tickNumber;

    @Override
    public void start(Stage stage) {
        island = new Island();
        new IslandPopulator().populate(island);
        simulationTick = new SimulationTick();
        statistics = new StatisticsService();
        tickNumber = new AtomicInteger(0);

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Вид");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Количество");

        chart = new BarChart<>(xAxis, yAxis);
        chart.setAnimated(false);
        chart.setLegendVisible(false);

        series = new XYChart.Series<>();
        chart.getData().add(series);

        dayLabel = new Label("День 0");
        dayLabel.getStyleClass().add("day-counter");
        dayLabel.setAlignment(Pos.CENTER_LEFT);

        updateChart();

        BorderPane root = new BorderPane();
        root.setTop(dayLabel);
        root.setCenter(chart);

        Scene scene = new Scene(root, 1100, 650);
        scene.getStylesheets().add(getClass().getResource("/island-style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Симуляция острова");
        stage.show();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(SimulationConfig.TICK_DURATION_MS), event -> runTick())
        );
        timeline.setCycleCount(MAX_TICKS);
        timeline.setOnFinished(event -> simulationTick.shutdown());
        timeline.play();

        stage.setOnCloseRequest(event -> simulationTick.shutdown());
    }

    private void runTick() {
        int currentTick = tickNumber.incrementAndGet();
        simulationTick.run(island);
        dayLabel.setText("День " + currentTick);
        updateChart();
    }

    private void updateChart() {
        series.getData().clear();
        Map<String, Integer> counts = statistics.countAnimals(island);
        counts.forEach(this::addBar);
        addBar("Plant", statistics.countPlants(island));
    }

    private void addBar(String type, int count) {
        String label = ICONS.getOrDefault(type, type);
        XYChart.Data<String, Number> data = new XYChart.Data<>(label, count);
        series.getData().add(data);
        styleBar(data, type, count);
    }

    private void styleBar(XYChart.Data<String, Number> data, String type, int count) {
        Node node = data.getNode();
        if (node != null) {
            applyBarStyle(node, type, count);
        } else {
            data.nodeProperty().addListener((obs, oldNode, newNode) -> {
                if (newNode != null) {
                    applyBarStyle(newNode, type, count);
                }
            });
        }
    }

    private void applyBarStyle(Node node, String type, int count) {
        node.setStyle("-fx-bar-fill: " + colorFor(type) + "; -fx-background-radius: 6 6 0 0;");
        Tooltip.install(node, new Tooltip(ICONS.getOrDefault(type, type) + ": " + count));
    }

    private String colorFor(String type) {
        if (type.equals("Plant")) {
            return "#6b8e23";
        }
        if (PREDATORS.contains(type)) {
            return "#c0392b";
        }
        if (OMNIVORES.contains(type)) {
            return "#e1b12c";
        }
        return "#2e86de";
    }

    public static void main(String[] args) {
        launch(args);
    }
}