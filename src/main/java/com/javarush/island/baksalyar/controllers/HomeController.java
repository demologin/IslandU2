package com.javarush.island.baksalyar.controllers;

import com.javarush.island.baksalyar.config.MessageBundle;
import com.javarush.island.baksalyar.config.Setting;
import com.javarush.island.baksalyar.managers.WindowManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * Controller for the home screen window. Provides navigation to the game start
 * screen (via the "Play" button) and to the settings window (via the "Settings" button).
 * <p>
 * Before starting the game, it checks that at least one participant (organism type)
 * is defined in the {@link Setting} class. If no participants are configured, an
 * error dialog is shown and the game does not proceed.
 * </p>
 */
public class HomeController {

    /** Button that initiates the game start process. */
    public Button playButton;
    /** Button that opens the settings window. */
    public Button settingsButton;

    /**
     * Initializes the controller after FXML loading.
     * Sets up button actions and localizes button texts.
     */
    public void initialize() {
        settingUpButtons();
        settingFields();
    }

    /**
     * Sets the localized text for the buttons using the message bundle.
     */
    private void settingFields() {
        playButton.setText(MessageBundle.get("home.name.buttonPlay"));
        settingsButton.setText(MessageBundle.get("button.name.settings"));
    }

    /**
     * Configures the action handlers for the buttons.
     * <ul>
     *   <li>{@link #playButton} calls {@link #startGame(ActionEvent)}.</li>
     *   <li>{@link #settingsButton} switches to the settings window.</li>
     * </ul>
     */
    private void settingUpButtons() {
        playButton.setOnAction(this::startGame);
        settingsButton.setOnAction(event -> WindowManager.switchWindow(
                Setting.NAME_SETTINGS_FILE,
                event,
                MessageBundle.get("window.name.settings")
        ));
    }

    /**
     * Handles the "Play" button action. Verifies that at least one participant
     * (organism type) is defined in the settings. If none are found, displays an
     * error alert and returns. Otherwise, switches to the game start window.
     *
     * @param event the action event triggered by the button
     */
    private void startGame(ActionEvent event) {
        Setting.get().verificationParticipants();
        if (Setting.get().getPARTICIPANTS().length == 0) {
            WindowManager.messageOutput(
                    MessageBundle.get("homeController.error.title.game"),
                    MessageBundle.get("homeController.error.message.game"),
                    Alert.AlertType.ERROR
            );
            return;
        }
        WindowManager.switchWindow(
                Setting.NAME_GAME_START_FILE,
                event,
                MessageBundle.get("window.name.startGame")
        );
    }
}
