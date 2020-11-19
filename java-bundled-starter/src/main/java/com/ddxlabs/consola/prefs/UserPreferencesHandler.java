package com.ddxlabs.consola.prefs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 *  Manage the state of UserPreferences
 *
 *  Write to disk when a preference changes.
 *
 */
public class UserPreferencesHandler {

    public static final String APP_USER_DIR = ".consola";
    private static final String PROPERTIES_FILE_NAME = "consola.properties";

    private UserPreferences prefs;

    public UserPreferencesHandler(UserPreferences defaultPrefs) {
        this.prefs = defaultPrefs;
    }

    public UserPreferences getPrefs() {
        return prefs;
    }

    /**
     * Initializes the preferences from disk
     *
     * @return
     */
    public UserPreferences loadPreferences() {
        try {
            File prefsFile = getPreferencesFile();

            Properties props = new Properties();
            FileReader reader = new FileReader(prefsFile);
            props.load(reader);
            if (!props.isEmpty()) {
                prefs.updateFromProperties(props);
            }
            reader.close();
        } catch (IOException e) {
            // not fatal, but print a stack trace
            e.printStackTrace();
        }
        return prefs;
    }

    /**
     *   Persists the preferences to disk.
     */
    public void savePreferences() {

        try {
            File prefsFile = getPreferencesFile();
            FileOutputStream fis = new FileOutputStream(prefsFile);
            prefs.getProperties().store(fis, "user preferences (do not edit unless you know what you are doing)");
            fis.close();
        } catch (IOException e) {
            // not fatal, but print out stack trace
            e.printStackTrace();
        }
    }

    public void setPreference(String key, String value) {
        prefs.setPreference(key, value);

        // when a preference is updated, persist to disk
        savePreferences();
    }


    /**
     *   Gets the preferences file from the application user folder,
     *   building out the path and file if it doesnt already exist
     *
     * @return
     * @throws IOException
     */
    private File getPreferencesFile() throws IOException {
        // load prefs from user.dir, if it exists
        String userDir = System.getProperty("user.home");

        File appUserDir = new File(userDir + System.getProperty("file.separator") + APP_USER_DIR);
        if (!appUserDir.exists()) {
            appUserDir.mkdir();
        }

        File prefsFile = new File(appUserDir.getAbsolutePath() + System.getProperty("file.separator") + PROPERTIES_FILE_NAME);
        if (!prefsFile.exists()) {
            prefsFile.createNewFile();
        }

        return prefsFile;
    }

}
