package com.javarush.island.baksalyar.config;

import java.util.Locale;
import java.util.ResourceBundle;
/**
 * Utility class for managing localized messages using {@link ResourceBundle}.
 * <p>
 * Loads resource bundles based on the configured locale (defaults to the system
 * locale). Provides static methods to change the locale and retrieve localized
 * strings by key.
 * </p>
 */
public class MessageBundle {

    /** The current resource bundle instance. */
    private static ResourceBundle bundle;

    static {
        setLocale(Locale.getDefault());
    }

    /**
     * Sets the current locale and reloads the resource bundle.
     *
     * @param locale the new locale for message localization
     */
    public static void setLocale(Locale locale) {
        bundle = ResourceBundle.getBundle(Setting.BASE_NAME, locale);
    }

    /**
     * Retrieves a localized message for the given key.
     *
     * @param key the message key
     * @return the localized string associated with the key
     * @throws java.util.MissingResourceException if the key is not found
     */
    public static String get(String key) {
        return bundle.getString(key);
    }
}
