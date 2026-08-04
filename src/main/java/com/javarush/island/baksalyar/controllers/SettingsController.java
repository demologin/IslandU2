package com.javarush.island.baksalyar.controllers;

import com.javarush.island.baksalyar.config.MessageBundle;
import com.javarush.island.baksalyar.config.Setting;
import com.javarush.island.baksalyar.entity.organizm.Organism;
import com.javarush.island.baksalyar.managers.WindowManager;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Controller for the settings window. Displays a list of organism prototypes
 * (defined in {@link Setting#PROTOTYPES}), each with its icon, class name,
 * and a button to edit its parameters.
 * <p>
 * The settings are presented as a vertical list of rows, where each row
 * contains a label with the organism's icon and simple name, and a button
 * that navigates to a detailed settings editor for that organism.
 * </p>
 */
public class SettingsController {

    /** Container (VBox) that holds the list of organism setting entries. */
    public VBox settingsContainer;
    /** Button to return to the home screen. */
    public Button backButton;

    /**
     * Initializes the controller after FXML loading.
     * Populates the settings list via {@link #uploadingData()} and sets
     * the back button action to navigate home.
     */
    public void initialize() {
        uploadingData();
        backButton.setOnAction(event -> WindowManager.switchWindow(
                Setting.NAME_HOME_FILE,
                event,
                MessageBundle.get("window.name.home")
        ));
    }

    /**
     * Populates the {@link #settingsContainer} with a row for each organism
     * prototype in {@link Setting#PROTOTYPES}.
     * <p>
     * For each organism, a {@link Label} is created showing its icon and
     * simple class name, and a {@link Button} labeled "Edit" (localized).
     * Clicking the button switches to the detailed settings editor window
     * ({@link Setting#NAME_SETTINGS_ELEMENT_FILE}) and passes the selected
     * organism via a new {@link SettingsElementController}.
     * </p>
     * <p>
     * The label uses a font size of 40 for better visibility.
     * </p>
     */
    private void uploadingData() {
        for (Organism animal : Setting.PROTOTYPES) {
            // Create a label with icon and class name
            String text = animal.getIcon() + " " + animal.getClass().getSimpleName();
            Label label = new Label(text);
            label.setFont(Font.font(40));

            // Create an "Edit" button that navigates to the detailed settings
            Button button = new Button(MessageBundle.get("button.name.settingsElement.settingAnimal"));
            button.setOnAction(event -> {
                WindowManager.switchWindow(
                        Setting.NAME_SETTINGS_ELEMENT_FILE,
                        MessageBundle.get("window.name.settingsElement"),
                        event,
                        new SettingsElementController(animal)
                );
            });

            // Wrap label and button in a VBox with spacing 20
            VBox row = new VBox(20, label, button);
            settingsContainer.getChildren().add(row);
        }
    }
}
