package com.ddxlabs.consola.prefs;

import com.ddxlabs.consola.view.ColorTheme;

import java.util.Properties;

/**
 *  Container for custom preferences
 */
public class UserPreferences {

    public static final String KEY_COLOR_THEME = "color.theme";
    public static final String KEY_BASE_FONT_SIZE = "text.base.font.size";
    public static final String KEY_BASE_PADDING = "text.base.padding";
    public static final String KEY_FONT_FAMILY = "text.font.family";
    public static final String KEY_MAX_HISTORY_LINES = "console.max.history.lines";

    private Properties prefs;

    public UserPreferences() {
        // defaults
        prefs = new Properties();

        prefs.setProperty(KEY_COLOR_THEME, ColorTheme.STD_LIGHT.name());
        prefs.setProperty(KEY_BASE_FONT_SIZE, "12");
        prefs.setProperty(KEY_MAX_HISTORY_LINES, "100");
        prefs.setProperty(KEY_BASE_PADDING, "5");
        prefs.setProperty(KEY_FONT_FAMILY, "Courier New");
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
    void setPreference(String prefKey, String value) {
        prefs.setProperty(prefKey, value);
    }

    public ColorTheme getColorTheme() {
        return ColorTheme.valueOf(prefs.getProperty(KEY_COLOR_THEME));
    }

    protected Properties getProperties() {
        return prefs;
    }

}
