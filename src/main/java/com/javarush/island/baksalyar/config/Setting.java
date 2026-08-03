package com.javarush.island.baksalyar.config;

import com.javarush.island.baksalyar.entity.organizm.Organism;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Objects;
/**
 * Global configuration and settings for the application.
 * <p>
 * Provides constants for file paths, FXML resources, message bundles,
 * and organism prototypes. Maintains runtime settings such as grid dimensions
 * (rows, columns) and the list of participating organisms.
 * </p>
 * <p>
 * The class is a thread-safe singleton accessed via {@link #get()}.
 * It also initializes organism prototypes using {@link OrganismScanner}
 * and dynamically filters participants via {@link #verificationParticipants()}.
 * </p>
 */
@Getter
@Setter
public class Setting {
    /** JSON file name pattern for organism configurations. */
    public static final String SETTING_JSON = "%s.json";
    /** Default JSON resource path (fallback). */
    public static final String SETTING_JSON_DEFAULT = "/com/javarush/island/baksalyar/config/standardSettings.json";
    /** Path for saving organism settings (user-modified JSON). */
    public static final String SAVE_JSON = "target/classes/com/javarush/island/baksalyar/config/%s.json";
    /** Root path for FXML files. */
    public static final String PATH_TO_FXML = "/com/javarush/island/baksalyar/javafx/";
    /** Base name for resource bundles (i18n). */
    public static final String BASE_NAME = "com.javarush.island.baksalyar.config.messages";

    /** Pre‑initialized array of all organism prototypes. */
    public static final Organism[] PROTOTYPES = OrganismScanner.createPrototypes();

    /** Singleton instance (lazy initialization). */
    public static volatile Setting SETTING;

    // --- FXML file names ---

    public static final String NAME_GAME_START_FILE = "GameStart.fxml";
    public static final String NAME_GAME_FILE = "Game.fxml";
    public static final String NAME_SETTINGS_FILE = "Settings.fxml";
    public static final String NAME_HOME_FILE = "Home.fxml";
    public static final String NAME_SETTINGS_ELEMENT_FILE = "SettingsElement.fxml";

    /** Package to scan for organism classes. */
    public static final String PATH_TO_ORGANISMS = "com.javarush.island.baksalyar.entity.organizm";

    /** CSS style for error highlighting on input fields. */
    public static final String ERROR_STYLE = "-fx-prompt-text-fill: #ff6b6b;";

    // --- Runtime settings ---

    private int rows;
    private int cols;
    private Organism[] PARTICIPANTS;
    /**
     * Returns the array of participating organisms.
     * Automatically refreshes the list by filtering prototypes.
     *
     * @return an array of organisms with {@code isParticipates()} == true
     */
    public Organism[] getPARTICIPANTS() {
        verificationParticipants();
        return PARTICIPANTS;
    }
    /**
     * Setter for PARTICIPANTS (currently a no‑op, kept for compatibility).
     *
     * @param PARTICIPANTS ignored
     */
    public void setPARTICIPANTS(Organism[] PARTICIPANTS) {}
    /**
     * Returns the singleton instance of Settings, creating it if necessary.
     * Thread‑safe using double‑checked locking.
     *
     * @return the global Settings instance
     */
    public static Setting get() {
        Setting setting = SETTING;
        if (Objects.isNull(setting)) {
            synchronized (Setting.class) {
                if (Objects.isNull(setting = SETTING)) {setting = SETTING = new Setting();}
            }
        }
        return setting;
    }
    /**
     * Filters the {@link #PROTOTYPES} array and stores only those
     * that are marked as participating into {@link #PARTICIPANTS}.
     */
    public void verificationParticipants(){
        PARTICIPANTS = Arrays.stream(PROTOTYPES).filter(c -> c.isParticipates()).toArray(Organism[]::new);
    }
}
