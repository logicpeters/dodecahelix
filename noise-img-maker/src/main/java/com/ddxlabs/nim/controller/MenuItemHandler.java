package com.ddxlabs.nim.controller;

import com.ddxlabs.nim.*;
import com.ddxlabs.nim.noise.modules.ComboQualifier;
import com.ddxlabs.nim.noise.modules.SourceQualifier;
import com.ddxlabs.nim.view.ColorTheme;
import com.ddxlabs.nim.view.Menu;

public class MenuItemHandler implements ControllerComponent {

    private final Views allViews;
    private final UserPreferencesHandler prefsHandler;
    private final SystemHandler systemHandler;

    public MenuItemHandler(Controllers controllers, Views views, Models models, UserPreferences prefs) {
        this.prefsHandler = controllers.getUserPreferencesHandler();
        this.systemHandler = controllers.getSystemHandler();
        this.allViews = views;
    }

    /**
     * Handles menu item selections
     *
     * @param menuItemId
     */
    public void processMenuItem(String menuItemId) {

        if (menuItemId.startsWith(Menu.MODULE_ADD)) {
            int lastUnderscore = menuItemId.lastIndexOf("_");
            String qualName = menuItemId.substring(lastUnderscore).toUpperCase();
            addModule(menuItemId, qualName);
            return;
        }

        switch (menuItemId) {
            // TODO - get rid of this cast/app dependency
            case Menu.APP_EXIT: systemHandler.exitApp(); break;
            case Menu.THEME_STD_DARK: updateTheme(ColorTheme.STD_DARK); break;
            case Menu.THEME_STD_LIGHT: updateTheme(ColorTheme.STD_LIGHT); break;
            case Menu.THEME_OCEAN: updateTheme(ColorTheme.OCEAN_DARK); break;
            default: System.err.println("Menu action " + menuItemId + " not implemented");
        }
    }

    private void addModule(String menuItemId, String qualName) {
        if (menuItemId.startsWith(Menu.MODULE_ADD_COMBO)) {
            ComboQualifier comboQualifier = ComboQualifier.valueOf(qualName);
        }
        if (menuItemId.startsWith(Menu.MODULE_ADD_COMBO)) {
            SourceQualifier sourceQualifier = SourceQualifier.valueOf(qualName);
        }
    }

    private void updateTheme(ColorTheme theme) {
        prefsHandler.setPreference(UserPreferences.KEY_COLOR_THEME, theme.name());

        allViews.applyPreferences();
    }

}
