package com.ddxlabs.nim.view;

import com.ddxlabs.nim.UserPreferences;
import com.ddxlabs.nim.controller.Controllers;
import com.ddxlabs.nim.view.GeneratorRow;
import com.ddxlabs.nim.view.ImagePreviewView;
import com.ddxlabs.nim.view.Menu;
import com.ddxlabs.nim.view.tabs.ModuleTabs;

public class Views {

    private UserPreferences preferences;

    private Menu menu;
    private GeneratorRow generatorRow;
    private ModuleTabs moduleTabs;
    private ImagePreviewView imageView;

    public Views(UserPreferences preferences) {
        this.preferences = preferences;
        this.menu = new Menu(preferences);
        this.generatorRow = new GeneratorRow(preferences);
        this.moduleTabs = new ModuleTabs(preferences);
        this.imageView = new ImagePreviewView(preferences);
    }

    public void init(Controllers controllers) {
        this.menu.init(controllers);
        this.moduleTabs.init(controllers);
        this.generatorRow.init(controllers);
        this.imageView.init(controllers);
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

    public ImagePreviewView getImageView() {
        return imageView;
    }

    /**
     *   These views should already have a reference to the preferences object with its changes
     *
     *   This method is just telling them to update their UI look and feel
     *   to reflect possible changes to these preferences.
     */
    public void applyPreferences() {
        menu.applyPreferences();
        moduleTabs.applyPreferences();
        generatorRow.applyPreferences();
        imageView.applyPreferences();
    }
}
