package com.javarush.island.baksalyar.controllers;

import com.javarush.island.baksalyar.config.Initialization;
import com.javarush.island.baksalyar.config.MessageBundle;
import com.javarush.island.baksalyar.config.Setting;
import com.javarush.island.baksalyar.config.View;
import com.javarush.island.baksalyar.creators.EntityCreator;
import com.javarush.island.baksalyar.creators.GameMapCreator;
import com.javarush.island.baksalyar.entity.Game;
import com.javarush.island.baksalyar.entity.map.Cell;
import com.javarush.island.baksalyar.entity.map.GameMap;
import com.javarush.island.baksalyar.entity.organizm.Organisms;
import com.javarush.island.baksalyar.managers.WindowManager;
import com.javarush.island.baksalyar.services.*;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for the game window. Implements the {@link View} interface to
 * receive UI update callbacks from the game logic. Manages the game field display,
 * statistics, pause/resume, and navigation back to the home screen.
 * <p>
 * It initializes the game map, starts background services (view, moving, eating,
 * reproducing) and updates the JavaFX UI components accordingly. All UI updates
 * are performed on the JavaFX Application Thread via {@link Platform#runLater}.
 * </p>
 */
public class GameController implements View {

    public Button saveButton;
    public Button backButton;
    public Text nameStatisticsText;
    public ScrollPane scrollPane;
    public Label statistics;
    public AnchorPane root;

    /** 2D array of labels representing each cell of the game map. */
    private Label[][] labelArray;
    /** The game instance. */
    private Game game;

    /**
     * Initializes the controller after the FXML is loaded.
     * Calls {@link #gameStarted()}, {@link #settingFields()}, and
     * {@link #buttonProcessing()} in order.
     */
    public void initialize() {
        gameStarted();
        settingFields();
        buttonProcessing();
    }

    /**
     * Creates the visual playing field as a {@link GridPane} inside the scroll pane.
     * The grid dimensions are obtained from {@link Setting}. Each cell is represented
     * by a {@link Label} stored in {@link #labelArray}.
     */
    private void creatingPlayingField() {
        labelArray = new Label[Setting.get().getRows()][Setting.get().getCols()];
        GridPane cellPanel = new GridPane();
        cellPanel.setPrefHeight(800);
        cellPanel.setPrefWidth(1024);
        cellPanel.setGridLinesVisible(true);
        for (int i = 0; i < Setting.get().getCols(); i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100d / Setting.get().getCols());
            col.setHgrow(Priority.ALWAYS);
            cellPanel.getColumnConstraints().add(col);
        }
        for (int i = 0; i < Setting.get().getRows(); i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100d / Setting.get().getRows());
            row.setVgrow(Priority.NEVER);
            cellPanel.getRowConstraints().add(row);
        }
        scrollPane.setContent(cellPanel);
        for (int i = 0; i < labelArray.length; i++) {
            for (int j = 0; j < labelArray[i].length; j++) {
                labelArray[i][j] = new Label();
                cellPanel.add(labelArray[i][j], j, i);
            }
        }
    }

    /**
     * Starts the game logic. Creates the game map, the {@link Game} instance,
     * and a list of services (view, moving, eating, reproducing) that are
     * executed periodically via {@link GameServiceProcessor}.
     * The processor is run on the JavaFX Application Thread.
     */
    private void gameStarted() {
        creatingPlayingField();
        Initialization entityFactory = new EntityCreator();
        GameMapCreator gameMapCreator = new GameMapCreator(entityFactory);
        GameMap gameMap = gameMapCreator.createRandomFilledGameMap(1);
        game = new Game(gameMap, entityFactory, this);

        List<Runnable> services = List.of(
                new ViewService(game),
                new MovingService(game),
                new EatingService(game),
                new ReproducingService(game)
        );
        GameServiceProcessor gameServiceProcessor = new GameServiceProcessor(services, game);
        Platform.runLater(gameServiceProcessor);
    }

    /**
     * Sets up event handlers for the buttons.
     * <ul>
     *   <li>{@link #saveButton} toggles the game pause state and changes its text.</li>
     *   <li>{@link #backButton} marks the game as finished and switches to the home window.</li>
     *   <li>Also sets a close request handler for the stage to mark the game as finished.</li>
     * </ul>
     */
    private void buttonProcessing() {
        saveButton.setOnAction(event -> {
            saveButton.setText(game.isPause() ? "⏸" : "▶");
            game.setPause(!game.isPause());
        });
        backButton.setOnAction(event -> {
            game.setFinished(true);
            WindowManager.switchWindow(Setting.NAME_HOME_FILE, event, MessageBundle.get("window.name.home"));
        });
        Platform.runLater(() -> {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setOnCloseRequest(event -> game.setFinished(true));
        });
    }

    /**
     * Sets the text of {@link #nameStatisticsText} using the message bundle.
     */
    private void settingFields() {
        nameStatisticsText.setText(MessageBundle.get("gameController.text.nameStatisticsText"));
    }


    /**
     * Called by the game to refresh the display. If the game is paused, the
     * update is skipped. Otherwise, calls {@link #showMap()} and {@link #showStatistics()}.
     */
    @Override
    public void show() {
        if (game.isPause()) {
            return;
        }
        showMap();
        showStatistics();
    }

    /**
     * Updates the statistics label with the current counts of organisms.
     * The text is built from the map's statistics and set on the JavaFX Application Thread.
     */
    @Override
    public void showStatistics() {
        String text = game.getGameMap()
                .getStatistics()
                .entrySet()
                .stream()
                .map(e -> e.getKey().getIcon() + "(" + e.getKey().getName() + "): " + e.getValue())
                .collect(Collectors.joining("\n"));
        Platform.runLater(() -> statistics.setText(text));
    }

    /**
     * Triggers the visual update of the game map.
     * Delegates to {@link #fillViewMap()} on the JavaFX Application Thread.
     */
    @Override
    public void showMap() {
        Platform.runLater(this::fillViewMap);
    }

    /**
     * Fills each cell label with the icons of the organisms present in that cell.
     * For each cell, up to three icons (sorted by population size descending) are displayed.
     * The lock of the cell is acquired to ensure thread-safe access to its residents.
     */
    private void fillViewMap() {
        Cell[][] cells = game.getGameMap().getCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                String text = getIcons(cells[i][j]);
                labelArray[i][j].setText(text);
            }
        }
    }

    /**
     * Retrieves a string representation of the organisms residing in a given cell.
     * The string is built by taking the three organism types with the largest populations
     * (in descending order) and concatenating their icons.
     *
     * @param cell the cell to examine
     * @return a concatenated string of organism icons (e.g., "🐑🐺🌿")
     */
    private String getIcons(Cell cell) {
        try {
            cell.getLock().lock();
            return cell.getResidents().values().stream()
                    .filter(list -> !list.isEmpty())
                    .sorted((o1, o2) -> o2.size() - o1.size())
                    .limit(3)
                    .map(Organisms::getIcon)
                    .collect(Collectors.joining());
        } finally {
            cell.getLock().unlock();
        }
    }
}
