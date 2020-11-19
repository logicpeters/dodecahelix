package com.ddxlabs.nim.controller;

import com.ddxlabs.nim.*;

public class SystemHandler {

    private Application app;

    public SystemHandler(Controllers controllers, Views views, Models models, UserPreferences prefs) {
        this.app = controllers.getApplication();
    }

    public void exitApp() {
        app.exitApp();
    }
}
