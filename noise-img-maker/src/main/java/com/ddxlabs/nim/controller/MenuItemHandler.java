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
import java.util.ArrayList;
import java.util.List;
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
        String[] idParts = menuItemId.split("_");

        if (idParts[0].equalsIgnoreCase("MODULE") && idParts[1].equals("ADD")) {
            addModule(menuItemId, idParts[2]);
            return;

        }

        if (idParts[0].equalsIgnoreCase("PRESET")) {
            Preset preset = Preset.valueOf(idParts[1]);
            this.importExportHandler.importPreset(preset);
            return;
        }

        if (idParts[0].equalsIgnoreCase("CHOP")) {
            int set = -1;
            int incVal = 0;
            if (idParts[1].equalsIgnoreCase("SET")) {
                set = Integer.parseInt(idParts[2]);
            }
            if (idParts[1].equalsIgnoreCase("INC")) {
                incVal = Integer.parseInt(idParts[2]);
            }
            if (idParts[1].equalsIgnoreCase("DEC")) {
                incVal = -Integer.parseInt(idParts[2]);
            }
            this.moduleHandler.incOrSetChopForStructure(set, incVal);
            return;
        }

        if (menuItemId.startsWith(Menu.MODULE_BUILD_COMBO)) {
            ComboQualifier cQual = ComboQualifier.valueOf(idParts[2].toUpperCase());
            SourceQualifier sQual = SourceQualifier.valueOf(idParts[3].toUpperCase());
            buildComboModule(cQual, sQual);
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

    private void buildComboModule(ComboQualifier cQual, SourceQualifier sQual) {
        String comboModuleId = moduleHandler.addComboModule(cQual);
        String sourceModuleId = moduleHandler.addSourceModule(sQual);
        String currentRootId = moduleHandler.getRootModule();
        // make the cQual the root
        // add the previous root to the combo
        moduleHandler.setRootModule(comboModuleId);
        List<String> sourceModules = new ArrayList<>();
        sourceModules.add(currentRootId);
        sourceModules.add(sourceModuleId);
        moduleHandler.setSourceModulesForCombo(comboModuleId, sourceModules);
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
