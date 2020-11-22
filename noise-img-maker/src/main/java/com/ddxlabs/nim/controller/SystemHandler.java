package com.ddxlabs.nim.controller;

import com.ddxlabs.nim.*;

public class SystemHandler implements ControllerComponent {

    private Application app;

    public SystemHandler(UserPreferences prefs) {
    }

    @Override
    public void init(Controllers controllers, Views views, Models models) {
    }

    public void setApp(Application app) {
        this.app = app;
    }

    public void exitApp() {
        app.exitApp();
    }

    public int getCurrentAppHeight() {
        return app.getFrameHeight();
    }

}
