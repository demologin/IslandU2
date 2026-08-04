package com.javarush.island.baksalyar.controllers;

import com.javarush.island.baksalyar.config.MessageBundle;
import com.javarush.island.baksalyar.config.Setting;
import com.javarush.island.baksalyar.managers.WindowManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Controller for the game start window. Manages user input for grid dimensions
 * (rows and columns) and handles the transition to the main game window upon
 * successful validation.
 * <p>
 * The input fields are validated to ensure they contain valid integers. If validation
 * fails, an error message is displayed and the user is prompted to correct the input.
 * On successful validation, the dimensions are stored in the {@link Setting} singleton
 * and the window switches to the game view.
 * </p>
 */
public class GameStartController {

    public TextField colsTextFieId;
    public Text heightText;
    public TextField rowsTextFieId;
    public Button startButton;
    public Text titleText;
    public Text widthText;

    /**
     * Initializes the controller after FXML loading.
     * Calls {@link #settingFields()} and {@link #buttonProcessing()}.
     */
    public void initialize() {
        settingFields();
        buttonProcessing();
    }

    /**
     * Sets the text of all UI labels and buttons using the message bundle.
     * This ensures internationalization support.
     */
    private void settingFields() {
        titleText.setText(MessageBundle.get("text.name.gameStart.titleText"));
        heightText.setText(MessageBundle.get("text.name.gameStart.heightText"));
        widthText.setText(MessageBundle.get("text.name.gameStart.widthText"));
        startButton.setText(MessageBundle.get("button.name.gameStart.startButton"));
    }

    /**
     * Checks whether the given string is a valid integer.
     * An empty string is considered valid (to allow the user to clear the field).
     *
     * @param str the input string to check
     * @return {@code true} if the string is <strong>not</strong> a valid integer
     *         (i.e., empty is treated as valid → returns {@code false}),
     *         {@code false} if parsing succeeds.
     * @implNote The method name is slightly misleading: it returns {@code true} when
     *           the string is NOT an integer (except for empty), effectively meaning
     *           "is invalid integer". This is used for validation error detection.
     */
    private static boolean isInteger(String str) {
        if (str.isEmpty()) return true;   // empty is considered "invalid" (needs input)
        try {
            Integer.parseInt(str);
            return false;   // parsing succeeded -> valid integer
        } catch (NumberFormatException e) {
            return true;    // parsing failed -> invalid
        }
    }

    /**
     * Validates the rows and columns input fields.
     * If either field contains an invalid value (non-integer or empty),
     * an error is displayed via {@link WindowManager#errorHandling} and the method returns {@code true}.
     *
     * @return {@code true} if validation fails (i.e., an error was shown), {@code false} if valid.
     */
    private boolean dataVerification() {
        if (isInteger(rowsTextFieId.getText())) {
            WindowManager.errorHandling(rowsTextFieId, MessageBundle.get("gameStartController.error.rowsTextFieId"));
            return true;
        }
        if (isInteger(colsTextFieId.getText())) {
            WindowManager.errorHandling(colsTextFieId, MessageBundle.get("gameStartController.error.colsTextFieId"));
            return true;
        }
        return false;
    }

    /**
     * Handles the start button action. Validates the input, updates the {@link Setting}
     * with the row and column values, and switches to the game window.
     *
     * @param event the action event triggered by the button
     */
    private void StartProcessing(ActionEvent event) {
        if (dataVerification()) {
            return;
        }
        Setting.get().setRows(Integer.parseInt(rowsTextFieId.getText()));
        Setting.get().setCols(Integer.parseInt(colsTextFieId.getText()));
        WindowManager.switchWindow(Setting.NAME_GAME_FILE, event, MessageBundle.get("window.name.game"));
    }

    /**
     * Sets the event handler for the start button.
     * When clicked, it calls {@link #StartProcessing(ActionEvent)}.
     */
    void buttonProcessing() {
        startButton.setOnAction(event -> StartProcessing(event));
    }
}
