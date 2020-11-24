package com.ddxlabs.nim.controller;

import com.ddxlabs.nim.*;
import com.ddxlabs.nim.noise.Models;
import com.ddxlabs.nim.noise.modules.ComboQualifier;
import com.ddxlabs.nim.noise.modules.SourceQualifier;
import com.ddxlabs.nim.utils.FileChooseUtils;
import com.ddxlabs.nim.view.ColorTheme;
import com.ddxlabs.nim.view.Menu;
import com.ddxlabs.nim.view.Views;

import java.io.File;
import java.util.Optional;

public class MenuItemHandler implements ControllerComponent {

    private Views allViews;
    private UserPreferencesHandler prefsHandler;
    private SystemHandler systemHandler;
    private ModuleHandler moduleHandler;
    private ImportExportHandler importExportHandler;

    public MenuItemHandler(UserPreferences prefs) {
    }

    public void init(Controllers controllers, Views views, Models models) {
        this.prefsHandler = controllers.getUserPreferencesHandler();
        this.systemHandler = controllers.getSystemHandler();
        this.moduleHandler = controllers.getModuleHandler();
        this.importExportHandler = controllers.getImportExportHandler();

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
            String qualName = menuItemId.substring(lastUnderscore + 1).toUpperCase();
            addModule(menuItemId, qualName);
            return;

        }
        if (menuItemId.startsWith("PRESET_")) {
            String presetName = menuItemId.substring(menuItemId.indexOf("_")+1);
            Preset preset = Preset.valueOf(presetName);
            this.importExportHandler.importPreset(preset);
            return;
        }

        switch (menuItemId) {
            // TODO - get rid of this cast/app dependency
            case Menu.FILE_OPEN: importExportHandler.importFile(); break;
            case Menu.FILE_SAVE: importExportHandler.exportFile(); break;
            case Menu.APP_EXIT: systemHandler.exitApp(); break;
            case Menu.THEME_STD_DARK: updateTheme(ColorTheme.STD_DARK); break;
            case Menu.THEME_STD_LIGHT: updateTheme(ColorTheme.STD_LIGHT); break;
            case Menu.THEME_OCEAN: updateTheme(ColorTheme.OCEAN_DARK); break;
            case Menu.PREFS_USE_BW:
            case Menu.PREFS_USE_COLOR:
            case Menu.PREFS_SET_IMAGE_EXPORT_DIR:
            case Menu.PREFS_SET_EXPORT_DIR:
                updatePref(menuItemId); break;
            default: System.err.println("Menu action " + menuItemId + " not implemented");
        }
    }

    private void updatePref(String menuItemId) {
        switch (menuItemId) {
            case Menu.PREFS_USE_BW: prefsHandler.setPreference(UserPreferences.KEY_IMAGE_COLOR, "false"); break;
            case Menu.PREFS_USE_COLOR: prefsHandler.setPreference(UserPreferences.KEY_IMAGE_COLOR, "true"); break;
            case Menu.PREFS_SET_EXPORT_DIR: {
                Optional<File> folder = FileChooseUtils.openFileChooserAndReturnFile(systemHandler.getFrame(),
                        false, true, "Folder", null);
                folder.ifPresent(file -> prefsHandler.setPreference(UserPreferences.KEY_IMAGE_FILE_FOLDER, file.getAbsolutePath()));
            }; break;
            case Menu.PREFS_SET_IMAGE_EXPORT_DIR: {
                Optional<File> folder = FileChooseUtils.openFileChooserAndReturnFile(systemHandler.getFrame(),
                        false, true, "Folder", null);
                folder.ifPresent(file -> prefsHandler.setPreference(UserPreferences.KEY_IMAGE_FOLDER, file.getAbsolutePath()));
            }; break;
        }
    }

    private void addModule(String menuItemId, String qualName) {
        if (menuItemId.startsWith(Menu.MODULE_ADD_COMBO)) {
            ComboQualifier comboQualifier = ComboQualifier.valueOf(qualName);
            moduleHandler.addComboModule(comboQualifier);
        }
        if (menuItemId.startsWith(Menu.MODULE_ADD_SOURCE)) {
            SourceQualifier sourceQualifier = SourceQualifier.valueOf(qualName);
            moduleHandler.addSourceModule(sourceQualifier);
        }
    }

    private void updateTheme(ColorTheme theme) {
        prefsHandler.setPreference(UserPreferences.KEY_COLOR_THEME, theme.name());

        allViews.applyPreferences();
    }

}
