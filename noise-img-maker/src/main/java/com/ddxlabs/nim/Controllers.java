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
    }

    public void init(Views views, Models models, Application application) {
        this.moduleHandler = new ModuleHandler(this, views, models, preferences);
        this.userPreferencesHandler = new UserPreferencesHandler(this, views, models, preferences);
        this.menuItemHandler = new MenuItemHandler(this, views, models, preferences);
        this.imageGenerationHandler = new ImageGenerationHandler(this, views, models, preferences);
        this.systemHandler = new SystemHandler(this, views, models, preferences);
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
