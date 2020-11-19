package com.ddxlabs.nim;

import com.ddxlabs.nim.controller.*;

public class Controllers {

    private UserPreferences preferences;

    private Application application;

    private ImageGenerationHandler imageGenerationHandler;
    private MenuItemHandler menuItemHandler;
    private ModuleHandler moduleHandler;
    private SystemHandler systemHandler;
    private UserPreferencesHandler userPreferencesHandler;

    public Controllers(UserPreferences preferences) {
        this.preferences = preferences;
        this.moduleHandler = new ModuleHandler(preferences);
        this.userPreferencesHandler = new UserPreferencesHandler(preferences);
        this.menuItemHandler = new MenuItemHandler(preferences);
        this.imageGenerationHandler = new ImageGenerationHandler(preferences);
        this.systemHandler = new SystemHandler(preferences);
    }

    public void init(Views views, Models models, Application application) {
        this.moduleHandler.init(this, views, models);
        this.userPreferencesHandler.init(this, views, models);
        this.menuItemHandler.init(this, views, models);
        this.imageGenerationHandler.init(this, views, models);
        this.systemHandler.init(this, views, models);
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

    public Application getApplication() {
        return application;
    }
}
