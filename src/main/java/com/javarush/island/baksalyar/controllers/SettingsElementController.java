package com.javarush.island.baksalyar.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.island.baksalyar.config.MessageBundle;
import com.javarush.island.baksalyar.config.Setting;
import com.javarush.island.baksalyar.entity.organizm.Limit;
import com.javarush.island.baksalyar.entity.organizm.Organism;
import com.javarush.island.baksalyar.entity.organizm.animals.Animal;
import com.javarush.island.baksalyar.managers.WindowManager;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Controller for the detailed settings editor of a single organism type.
 * Allows the user to view and modify properties such as name, icon, movement speed,
 * weight limits, saturation, and participation status. For animals, also displays
 * the "power" attribute.
 * <p>
 * The controller loads data from the provided {@link Organism} instance and its
 * associated {@link Limit} object. Changes are validated and saved to a JSON file
 * using Jackson {@link ObjectMapper}. The file path is constructed from the
 * organism's class name using the pattern defined in {@link Setting#SAVE_JSON}.
 * </p>
 * <p>
 * This class is instantiated with an organism via its constructor and is typically
 * passed to the window manager when switching to the settings element view.
 * </p>
 */
public class SettingsElementController {

    @Setter
    private Organism organism;
    private Limit limit;
    public TextField SpeedTextFieId;
    public Button backButton;
    public Text emojiText;
    public TextField iconTextFieId;
    public Text maxNumberPerSquareText;
    public Text maxSaturationText;
    public TextField maximumFoodTextFieId;
    public Text movementSpeedText;
    public Text nameText;
    public TextField nameTextFieId;
    public CheckBox participatesCheckBox;
    public Text participatesText;
    public Text powerText;
    public TextField powerTextFieId;
    public TextField quantityPerCellTextFieId;
    public Button saveButton;
    public Text startingWeightText;
    public TextField weightTextFieId;

    /**
     * Constructs a controller for editing the given organism.
     * Caches the organism's limit for later use.
     *
     * @param organism the organism to be edited
     */
    public SettingsElementController(Organism organism) {
        this.organism = organism;
        this.limit = organism.getLimit();
    }

    /**
     * Initializes the controller after FXML loading.
     * Calls {@link #settingUpButtons()}, {@link #getData()}, and {@link #uploadingForm()}
     * to set up UI, load current values, and adjust visibility.
     */
    public void initialize() {
        settingUpButtons();
        getData();
        uploadingForm();
    }

    /**
     * Configures the form fields and adjusts visibility based on organism type.
     * If the organism is an {@link Animal}, the power fields are shown; otherwise hidden.
     */
    public void uploadingForm() {
        settingFields();
        boolean check = (organism instanceof Animal);
        powerTextFieId.setVisible(check);
        powerText.setVisible(check);
    }

    /**
     * Sets localized texts for all labels and buttons using the message bundle.
     */
    private void settingFields() {
        nameText.setText(MessageBundle.get("settingsElementController.text.nameText"));
        startingWeightText.setText(MessageBundle.get("settingsElementController.text..startingWeightText"));
        maxNumberPerSquareText.setText(MessageBundle.get("settingsElementController.text.maxNumberPerSquareText"));
        movementSpeedText.setText(MessageBundle.get("settingsElementController.text.movementSpeedText"));
        maxSaturationText.setText(MessageBundle.get("settingsElementController.text.maxSaturationText"));
        emojiText.setText(MessageBundle.get("settingsElementController.text.emojiText"));
        saveButton.setText(MessageBundle.get("settingsElementController.name.saveButton"));
        participatesText.setText(MessageBundle.get("settingsElementController.text.participatesText"));
        powerText.setText(MessageBundle.get("settingsElementController.text.powerText"));
    }

    /**
     * Sets up action handlers for the back and save buttons.
     * <ul>
     *   <li>Back button returns to the main settings window.</li>
     *   <li>Save button triggers {@link #savingData()}.</li>
     * </ul>
     */
    private void settingUpButtons() {
        backButton.setOnAction(event -> {
            WindowManager.switchWindow(Setting.NAME_SETTINGS_FILE, event, MessageBundle.get("window.name.settings"));
        });
        saveButton.setOnAction(event -> savingData());
    }

    /**
     * Checks if the given string can be parsed as a double.
     * An empty string is considered invalid (returns {@code true}).
     *
     * @param str the string to check
     * @return {@code true} if the string is empty or not a valid double,
     *         {@code false} if it parses successfully.
     */
    private static boolean isDouble(String str) {
        if (str.isEmpty()) return true;
        try {
            Double.parseDouble(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    /**
     * Validates all input fields.
     * Checks that numeric fields contain valid numbers, and that the icon
     * and name fields are not empty. If any validation fails, an error is
     * displayed using {@link WindowManager#errorHandling} and the method
     * returns {@code true}.
     *
     * @return {@code true} if validation failed (i.e., errors present),
     *         {@code false} if all inputs are valid.
     */
    private boolean dataVerification() {
        if (isDouble(weightTextFieId.getText())) {
            WindowManager.errorHandling(weightTextFieId, MessageBundle.get("settingsElementController.error.startWeight"));
            return true;
        }
        if (isDouble(quantityPerCellTextFieId.getText())) {
            WindowManager.errorHandling(quantityPerCellTextFieId, MessageBundle.get("settingsElementController.error.maxNumberPerCell"));
            return true;
        }
        if (isDouble(SpeedTextFieId.getText())) {
            WindowManager.errorHandling(SpeedTextFieId, MessageBundle.get("settingsElementController.error.maxSpeed"));
            return true;
        }
        if (isDouble(maximumFoodTextFieId.getText())) {
            WindowManager.errorHandling(maximumFoodTextFieId, MessageBundle.get("settingsElementController.error.maxWeight"));
            return true;
        }
        if (iconTextFieId.getText().isEmpty()) {
            WindowManager.errorHandling(iconTextFieId, MessageBundle.get("settingsElementController.error.icon"));
            return true;
        }
        if (nameTextFieId.getText().isEmpty()) {
            WindowManager.errorHandling(nameTextFieId, MessageBundle.get("settingsElementController.error.name"));
            return true;
        }
        if (isDouble(powerTextFieId.getText())) {
            WindowManager.errorHandling(nameTextFieId, MessageBundle.get("settingsElementController.error.powerTextFieId"));
            return true;
        }
        return false;
    }

    /**
     * Saves the current settings to the organism object, its limit, and persists
     * them to a JSON file. Validates input before saving. On successful save,
     * displays an information dialog. On error, shows an error dialog and throws
     * a runtime exception.
     * <p>
     * The file is saved under the path defined by {@link Setting#SAVE_JSON},
     * with the filename derived from the organism's class simple name.
     * </p>
     */
    private void savingData() {
        if (dataVerification()) {
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        limit.setMaxSpeed(Integer.parseInt(this.SpeedTextFieId.getText()));
        organism.setName(this.nameTextFieId.getText());
        organism.setPower(Integer.parseInt(this.powerTextFieId.getText()));
        organism.setIcon(this.iconTextFieId.getText());
        organism.setParticipates(this.participatesCheckBox.isSelected());
        limit.setMaxFood(Double.parseDouble(this.maximumFoodTextFieId.getText()));
        limit.setMaxCountInCell(Integer.parseInt(this.quantityPerCellTextFieId.getText()));
        limit.setMaxWeight(Double.parseDouble(this.weightTextFieId.getText()));
        try {
            Path path = Paths.get(
                    String.format(
                            Setting.SAVE_JSON,
                            organism.getClass().getSimpleName()
                    )
            );
            Files.createDirectories(path.getParent());
            mapper.writeValue(path.toFile(), organism);
            WindowManager.messageOutput(
                    MessageBundle.get("settingsElementController.title.save"),
                    MessageBundle.get("settingsElementController.message.save"),
                    Alert.AlertType.INFORMATION
            );
        } catch (IOException e) {
            WindowManager.messageOutput(
                    MessageBundle.get("settingsElementController.error.title.save"),
                    MessageBundle.get("settingsElementController.error.message.save"),
                    Alert.AlertType.ERROR
            );
            throw new RuntimeException(e);
        }
    }

    /**
     * Populates the form fields with the current data from the organism and its limit.
     * Also sets the power field if the organism is an animal.
     */
    private void getData() {
        SpeedTextFieId.setText(limit.getMaxSpeed() + "");
        iconTextFieId.setText(organism.getIcon());
        maximumFoodTextFieId.setText(limit.getMaxFood() + "");
        quantityPerCellTextFieId.setText(limit.getMaxCountInCell() + "");
        weightTextFieId.setText(limit.getMaxWeight() + "");
        nameTextFieId.setText(organism.getName());
        participatesCheckBox.setSelected(organism.isParticipates());
        if (organism instanceof Animal animal) {
            powerTextFieId.setText(animal.getPower() + "");
        }
    }
}