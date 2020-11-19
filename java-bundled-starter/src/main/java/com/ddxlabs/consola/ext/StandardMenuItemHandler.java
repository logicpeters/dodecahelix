package com.ddxlabs.consola.ext;

import com.ddxlabs.consola.ExitHandler;
import com.ddxlabs.consola.MenuItemHandler;
import com.ddxlabs.consola.prefs.UserPreferences;
import com.ddxlabs.consola.prefs.UserPreferencesHandler;
import com.ddxlabs.consola.view.*;

public class StandardMenuItemHandler implements MenuItemHandler {

    private ViewContainer app;
    private UserPreferencesHandler prefsHandler;
    private ExitHandler exitHandler;

    public StandardMenuItemHandler(ViewContainer app, UserPreferencesHandler prefsHandler, ExitHandler exitHandler) {
        this.app = app;
        this.prefsHandler = prefsHandler;
        this.exitHandler = exitHandler;
    }

    /**
     * Handles menu item selections
     *
     * @param menuItemId
     */
    public void processMenuItem(String menuItemId) {
        switch (menuItemId) {
            // TODO - get rid of this cast/app dependency
            case Menu.APP_EXIT: exitHandler.exit(); break;

            case Menu.THEME_STD_DARK: updateTheme(ColorTheme.STD_DARK); break;
            case Menu.THEME_STD_LIGHT: updateTheme(ColorTheme.STD_LIGHT); break;
            case Menu.THEME_OCEAN: updateTheme(ColorTheme.OCEAN_DARK); break;
            case Menu.VIEW_INC_FONT: updateBaseFont(1); break;
            case Menu.VIEW_DEC_FONT: updateBaseFont(-1); break;

            default: System.err.println("Menu action " + menuItemId + " not implemented");
        }
    }

    private void updateTheme(ColorTheme theme) {
        prefsHandler.setPreference(UserPreferences.KEY_COLOR_THEME, theme.name());
        for (ViewComponent view: this.app.allViews()) {
            view.applyPreferences(prefsHandler.getPrefs());
        }
    }

    private void updateBaseFont(int incrementAmount) {
        int currentFontSize = prefsHandler.getPrefs().getIntPreference(UserPreferences.KEY_BASE_FONT_SIZE);
        int newFontSize = currentFontSize + 2 * incrementAmount;
        prefsHandler.setPreference(UserPreferences.KEY_BASE_FONT_SIZE, String.valueOf(newFontSize));
        for (ViewComponent view: this.app.allViews()) {
            view.applyPreferences(prefsHandler.getPrefs());
        }
    }
}
