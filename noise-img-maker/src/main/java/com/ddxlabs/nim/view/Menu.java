package com.ddxlabs.nim.view;

import com.ddxlabs.nim.Preset;
import com.ddxlabs.nim.controller.Controllers;
import com.ddxlabs.nim.controller.MenuItemHandler;
import com.ddxlabs.nim.UserPreferences;
import com.ddxlabs.nim.noise.modules.ComboQualifier;
import com.ddxlabs.nim.noise.modules.SourceQualifier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created on 5/30/2019.
 */
public class Menu implements ViewComponent {

    private UserPreferences preferences;

    public static final String FILE_OPEN = "file_open";
    public static final String FILE_SAVE = "file_save";
    public static final String APP_EXIT = "app_exit";
    public static final String THEME_STD_DARK = "theme_std_dark";
    public static final String THEME_STD_LIGHT = "theme_std_light";
    public static final String THEME_OCEAN = "theme_ocean_dark";
    public static final String HELP_ABOUT = "help_about";

    public static final String MODULE_ADD = "add_module_";
    public static final String MODULE_ADD_SOURCE = "add_module_source_";
    public static final String MODULE_ADD_COMBO = "add_module_combo_";
    public static final String MODULE_BUILD_COMBO = "build_combo";

    public static final String PREFS_USE_COLOR = "prefs_use_color";
    public static final String PREFS_USE_BW = "prefs_use_bw";
    public static final String PREFS_SET_IMAGE_EXPORT_DIR = "prefs_set_image_dir";
    public static final String PREFS_SET_EXPORT_DIR = "prefs_set_file_dir";

    private MenuItemHandler menuHandler;

    private JMenuBar menuBar;

    public Menu(UserPreferences preferences) {
        this.preferences = preferences;
    }

    public void init(Controllers controllers) {
        this.menuHandler = controllers.getMenuItemHandler();
    }

    public JMenuBar buildUI() {
        menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createLineBorder(Color.black));

        JMenu fileMenu = new JMenu("File");
        this.addMenuItem(fileMenu, "Open / Import", FILE_OPEN, KeyEvent.VK_O);
        this.addMenuItem(fileMenu, "Save / Export", FILE_SAVE, KeyEvent.VK_S);
        fileMenu.add(new JSeparator());
        this.addMenuItem(fileMenu, "Exit", APP_EXIT, 0);
        menuBar.add(fileMenu);

        JMenu moduleMenu = new JMenu("Modules");
        JMenu addSourceMenu = new JMenu("Add Source");
        moduleMenu.add(addSourceMenu);
        for (SourceQualifier squal : SourceQualifier.values()) {
            this.addMenuItem(addSourceMenu, squal.name(), MODULE_ADD_SOURCE + squal.name().toLowerCase(), 0);
        }
        JMenu addComboMenu = new JMenu("Add Combo");
        moduleMenu.add(addComboMenu);
        for (ComboQualifier cqual : ComboQualifier.values()) {
            this.addMenuItem(addComboMenu, cqual.name(), MODULE_ADD_COMBO + cqual.name().toLowerCase(), 0);
        }

        JMenu buildComboMenu = new JMenu("Combine");
        for (ComboQualifier cqual : ComboQualifier.values()) {
            JMenu buildComboSubmenu = new JMenu(cqual.name());
            for (SourceQualifier squal : SourceQualifier.values()) {
                String menuItemId = String.format("%s_%s_%s", MODULE_BUILD_COMBO, cqual.name().toLowerCase(), squal.name().toLowerCase());
                this.addMenuItem(buildComboSubmenu, squal.name(), menuItemId, 0);
            }
            buildComboMenu.add(buildComboSubmenu);
        }
        moduleMenu.add(buildComboMenu);

        menuBar.add(moduleMenu);

        JMenu presetsMenu = new JMenu("Presets");
        for (Preset preset: Preset.values()) {
            this.addMenuItem(presetsMenu, preset.getDisplay(), "PRESET_" + preset.name(), 0);
        }

        menuBar.add(presetsMenu);

        JMenu prefsMenu = new JMenu("Preferences");
        menuBar.add(prefsMenu);
        this.addMenuItem(prefsMenu, "Use Color", PREFS_USE_COLOR, 0);
        this.addMenuItem(prefsMenu, "Use B/W", PREFS_USE_BW, 0);
        this.addMenuItem(prefsMenu, "Set File Export Directory", PREFS_SET_EXPORT_DIR, 0);
        this.addMenuItem(prefsMenu, "Set Image Export Directory", PREFS_SET_IMAGE_EXPORT_DIR, 0);

        JMenu viewMenu = new JMenu("View");
        JMenu themeMenu = new JMenu("Themes");
        viewMenu.add(themeMenu);
        this.addMenuItem(themeMenu, "Standard Dark", THEME_STD_DARK, 0);
        this.addMenuItem(themeMenu, "Standard Light", THEME_STD_LIGHT, 0);
        this.addMenuItem(themeMenu, "Ocean", THEME_OCEAN, 0);
        menuBar.add(viewMenu);

        JMenu helpMenu = new JMenu("Help");
        this.addMenuItem(helpMenu, "About", HELP_ABOUT, 0);
        menuBar.add(helpMenu);

        return menuBar;
    }

    @Override
    public void applyPreferences() {
    }

    private void addMenuItem(JMenu menu, String itemTitle, String itemId, int mnemonic) {
        JMenuItem menuItem = new JMenuItem(itemTitle);
        if (mnemonic>0) {
            menuItem.setMnemonic(mnemonic);
        }
        menuItem.addActionListener(e ->
            menuHandler.processMenuItem(itemId)
        );
        menu.add(menuItem);
    }




}
