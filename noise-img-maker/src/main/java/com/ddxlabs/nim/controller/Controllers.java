package com.ddxlabs.nim.controller;

import com.ddxlabs.nim.noise.Models;
import com.ddxlabs.nim.UserPreferences;
import com.ddxlabs.nim.view.Views;

public class Controllers {

    private UserPreferences preferences;

    private ImageGenerationHandler imageGenerationHandler;
    private MenuItemHandler menuItemHandler;
    private ModuleHandler moduleHandler;
    private SystemHandler systemHandler;
    private UserPreferencesHandler userPreferencesHandler;
    private ImportExportHandler importExportHandler;

    public Controllers(UserPreferences preferences) {
        this.preferences = preferences;
        this.moduleHandler = new ModuleHandler(preferences);
        this.userPreferencesHandler = new UserPreferencesHandler(preferences);
        this.menuItemHandler = new MenuItemHandler(preferences);
        this.imageGenerationHandler = new ImageGenerationHandler(preferences);
        this.systemHandler = new SystemHandler(preferences);
        this.importExportHandler = new ImportExportHandler(preferences);
    }

    public void init(Views views, Models models) {
        this.moduleHandler.init(this, views, models);
        this.userPreferencesHandler.init(this, views, models);
        this.menuItemHandler.init(this, views, models);
        this.imageGenerationHandler.init(this, views, models);
        this.systemHandler.init(this, views, models);
        this.importExportHandler.init(this, views, models);
    }

    public ImageGenerationHandler getImageGenerationHandler() {
        return imageGenerationHandler;
    }

    public MenuItemHandler getMenuItemHandler() {
        return menuItemHandler;
    }

    public ModuleHandler getModuleHandler() {
        return moduleHandler;
    }

    public SystemHandler getSystemHandler() {
        return systemHandler;
    }

    public UserPreferencesHandler getUserPreferencesHandler() {
        return userPreferencesHandler;
    }

    public ImportExportHandler getImportExportHandler() {
        return importExportHandler;
    }
}
