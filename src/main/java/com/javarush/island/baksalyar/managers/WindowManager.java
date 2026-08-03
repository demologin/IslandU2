package com.javarush.island.baksalyar.managers;

import com.javarush.island.baksalyar.config.Setting;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Utility class for managing JavaFX window navigation, dialogs, and input error handling.
 * <p>
 * Provides static methods to switch between scenes (windows), display modal alerts,
 * and highlight invalid input fields. The class follows a utility pattern with a
 * private constructor to prevent instantiation.
 * </p>
 * <p>
 * All {@code switchWindow} methods hide the current window (if triggered by an
 * {@link ActionEvent}) and create a new {@link Stage} with the specified FXML
 * resource. They support optional controller injection for custom initialization.
 * </p>
 */
public class WindowManager {

    /**
     * Private constructor to enforce non-instantiability of this utility class.
     */
    private WindowManager() {
    }

    /**
     * Switches to a new window using an {@link ActionEvent} to obtain the current stage.
     * The current window is hidden, and a new stage is created with the given FXML and title.
     *
     * @param fxmlPath the path to the FXML file (relative to {@link Setting#PATH_TO_FXML})
     * @param event    the ActionEvent that triggered the switch (used to get the current window)
     * @param title    the title to display on the new window
     */
    public static void switchWindow(String fxmlPath, ActionEvent event, String title) {
        ((Node) event.getSource()).getScene().getWindow().hide();
        switchWindow(fxmlPath, title, null);
    }

    /**
     * Switches to a new window with an optional custom controller.
     * The new stage is created, loaded with the specified FXML, and displayed.
     *
     * @param fxmlPath   the path to the FXML file (relative to {@link Setting#PATH_TO_FXML})
     * @param title      the title to display on the new window
     * @param controller an optional controller instance to use instead of the one defined in FXML
     */
    public static void switchWindow(String fxmlPath, String title, Object controller) {
        try {
            FXMLLoader loader = new FXMLLoader(WindowManager.class.getResource(Setting.PATH_TO_FXML + fxmlPath));
            if (controller != null) {
                loader.setController(controller);
            }
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML: " + Setting.PATH_TO_FXML + fxmlPath, e);
        }
    }

    /**
     * Switches to a new window, hiding the current one, and sets a custom controller.
     * This overload combines the event-based hiding with the controller injection.
     *
     * @param fxml       the path to the FXML file
     * @param title      the title of the new window
     * @param event      the ActionEvent used to hide the current window
     * @param controller the custom controller instance for the new window
     */
    public static void switchWindow(String fxml, String title, ActionEvent event, Object controller) {
        ((Node) event.getSource()).getScene().getWindow().hide();
        switchWindow(fxml, title, controller);
    }

    /**
     * Displays a modal alert dialog with the specified title, message, and alert type.
     *
     * @param title     the dialog title
     * @param message   the content text of the alert
     * @param alertType the type of alert (e.g., INFORMATION, ERROR, WARNING)
     */
    public static void messageOutput(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Highlights an input field with an error style and sets a prompt message.
     * The field's text is cleared, and its style is set to the error style defined
     * in {@link Setting#ERROR_STYLE}.
     *
     * @param textField the text field to mark as erroneous
     * @param message   the error message to display as the prompt text
     */
    public static void errorHandling(TextField textField, String message) {
        textField.setStyle(Setting.ERROR_STYLE);
        textField.setPromptText(message);
        textField.setText("");
    }
}

