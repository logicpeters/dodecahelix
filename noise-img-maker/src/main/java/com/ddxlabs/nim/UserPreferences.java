package com.ddxlabs.nim;

import com.ddxlabs.nim.view.ColorTheme;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 *  Container for custom preferences
 */
public class UserPreferences {

    private static final String APP_USER_DIR = ".nim";
    private static final String PROPERTIES_FILE_NAME = "nim.properties";

    public static final String KEY_COLOR_THEME = "color.theme";
    public static final String KEY_BASE_FONT_SIZE = "text.base.font.size";
    public static final String KEY_BASE_PADDING = "text.base.padding";
    public static final String KEY_FONT_FAMILY = "text.font.family";
    public static final String KEY_MAX_HISTORY_LINES = "console.max.history.lines";

    public static final String KEY_APPLICATION_FOLDER = "application.folder";
    public static final String KEY_IMAGE_FOLDER = "application.image.folder";
    public static final String KEY_IMAGE_FILE_FOLDER = "application.image.file.folder";

    public static final String KEY_IMAGE_CHOP = "image.chop";
    public static final String KEY_IMAGE_COLOR = "image.color";
    public static final String KEY_IMAGE_PIXEL_SIZE = "image.pixel.size";
    public static final String KEY_IMAGE_PERIOD = "image.period";
    public static final String KEY_IMAGE_LOW_LIMIT_KB = "image.low.limit.kb";
    public static final String KEY_IMAGE_HIGH_LIMIT_KB = "image.high.limit.kb";

    private Properties prefs;

    public UserPreferences() {
        // defaults
        prefs = new Properties();

        prefs.setProperty(KEY_COLOR_THEME, ColorTheme.STD_LIGHT.name());
        prefs.setProperty(KEY_BASE_FONT_SIZE, "12");
        prefs.setProperty(KEY_MAX_HISTORY_LINES, "100");
        prefs.setProperty(KEY_BASE_PADDING, "5");
        prefs.setProperty(KEY_FONT_FAMILY, "Courier New");

        prefs.setProperty(KEY_IMAGE_CHOP, "5");
        prefs.setProperty(KEY_IMAGE_PIXEL_SIZE, "1024");
        prefs.setProperty(KEY_IMAGE_PERIOD, "128");
        prefs.setProperty(KEY_IMAGE_LOW_LIMIT_KB, "0");
        prefs.setProperty(KEY_IMAGE_HIGH_LIMIT_KB, "500");
        prefs.setProperty(KEY_IMAGE_COLOR, "true");

        // these are default paths - should be reset elsewhere
        prefs.setProperty(KEY_APPLICATION_FOLDER, System.getProperty("user.home"));
        prefs.setProperty(KEY_IMAGE_FOLDER, System.getProperty("user.home"));
        prefs.setProperty(KEY_IMAGE_FILE_FOLDER, System.getProperty("user.home"));
    }

    public UserPreferences(Properties prefMap) {
        prefs = prefMap;
    }

    public void updateFromProperties(Properties props) {
        props.keySet().stream().forEach(key -> {
            prefs.setProperty((String) key, props.getProperty((String) key));
        });
    }

    public String getPreference(String prefKey) {
        return prefs.getProperty(prefKey);
    }

    public int getIntPreference(String prefKey) {
        return Integer.parseInt(prefs.getProperty(prefKey));
    }

    /**
     *  Set a preference.  Note that this is default visiblity,
     *  Only the UserPreferencesHandler should be calling this method.
     *
     * @param prefKey
     * @param value
     */
    public void setPreference(String prefKey, String value) {
        prefs.setProperty(prefKey, value);
    }

    public ColorTheme getColorTheme() {
        return ColorTheme.valueOf(prefs.getProperty(KEY_COLOR_THEME));
    }

    public Properties getProperties() {
        return prefs;
    }

    public void loadFromDisk() {
        try {
            File prefsFile = getPreferencesFile();

            Properties props = new Properties();
            FileReader reader = new FileReader(prefsFile);
            props.load(reader);
            if (!props.isEmpty()) {
                updateFromProperties(props);
            }
            reader.close();

            // save the file -- this might be the first time accessing it.
            savePreferences();
        } catch (IOException e) {
            // not fatal, but print a stack trace
            e.printStackTrace();
            System.out.println("unable to load preferences from disk -- using default properties");
        }
    }


    /**
     *   Persists the preferences to disk.
     */
    public void savePreferences() {
        try {
            File prefsFile = getPreferencesFile();
            FileOutputStream fis = new FileOutputStream(prefsFile);
            prefs.store(fis, "user preferences (do not edit unless you know what you are doing)");
            fis.close();
        } catch (IOException e) {
            // not fatal, but print out stack trace
            e.printStackTrace();
        }
    }

    /**
     *   Gets the preferences file from the application user folder,
     *   building out the path and file if it doesnt already exist
     *
     * @return
     * @throws IOException
     */
    private File getPreferencesFile() throws IOException {
        String applicationUserDirectory = getApplicationUserDirectory();
        File prefsFile = new File(applicationUserDirectory
                + System.getProperty("file.separator")
                + PROPERTIES_FILE_NAME);
        if (!prefsFile.exists()) {
            prefsFile.createNewFile();
        }
        return prefsFile;
    }

    private String getApplicationUserDirectory() {
        // load from user.dir, if it exists
        String userDir = System.getProperty("user.home");
        File appUserDir = new File(userDir
                + System.getProperty("file.separator")
                + APP_USER_DIR);
        if (!appUserDir.exists()) {
            appUserDir.mkdir();
        }
        return appUserDir.getAbsolutePath();
    }

}
