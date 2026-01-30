package com.itsericfrisk.blad.utils;

import java.util.prefs.Preferences;

public class AppPreferences {
    private static final Preferences prefs = Preferences.userNodeForPackage(AppPreferences.class);

    private AppPreferences() {}

    /**
     * Save the last used file
     * @param filePath to the files location
     */
    public static void saveLastFile(String filePath) {
        prefs.put("last_opened_file", filePath);
    }

    /**
     * Get the last used file
     * @return A file or null
     */
    public static String getLastFile() {
        return prefs.get("last_opened_file", null);
    }
}
