package com.ddxlabs.nim;

import com.ddxlabs.nim.view.GeneratorRow;
import com.ddxlabs.nim.view.Menu;
import com.ddxlabs.nim.view.tabs.ModuleTabs;

public class Views {

    private UserPreferences preferences;

    private Menu menu;
    private GeneratorRow generatorRow;
    private ModuleTabs moduleTabs;

    public Views(UserPreferences preferences) {
        this.preferences = preferences;
    }

    public void init(Controllers controllers) {
        this.menu = new Menu(preferences, controllers);
        this.moduleTabs = new ModuleTabs(preferences, controllers);
        this.generatorRow = new GeneratorRow(preferences, controllers);
    }

    public Menu getMenu() {
        return menu;
    }

    public GeneratorRow getGeneratorRow() {
        return generatorRow;
    }

    public ModuleTabs getModuleTabs() {
        return moduleTabs;
    }

    /**
     *   These views should already have a reference to the preferences object with its changes
     *
     *   This method is just telling them to update their UI look and feel
     *   to reflect possible changes to these preferences.
     */
    public void applyPreferences() {
        menu.applyPreferences(preferences);
        moduleTabs.applyPreferences(preferences);
        generatorRow.applyPreferences(preferences);
    }
}
