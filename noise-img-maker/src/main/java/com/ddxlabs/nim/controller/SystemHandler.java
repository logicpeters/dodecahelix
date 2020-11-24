package com.ddxlabs.nim.controller;

import com.ddxlabs.nim.Application;
import com.ddxlabs.nim.UserPreferences;
import com.ddxlabs.nim.noise.Models;
import com.ddxlabs.nim.view.Views;
import com.ddxlabs.nim.view.tabs.ModuleTabs;

import javax.swing.*;

public class SystemHandler implements ControllerComponent {

    private Application app;
    private ImageGenerationHandler imgeGenHandler;
    private ModuleHandler moduleHandler;
    private ModuleTabs moduleTabs;

    public SystemHandler(UserPreferences prefs) {
    }

    @Override
    public void init(Controllers controllers, Views views, Models models) {
        this.moduleHandler = controllers.getModuleHandler();
        this.moduleTabs = views.getModuleTabs();
        this.imgeGenHandler = controllers.getImageGenerationHandler();
    }

    public void setApp(Application app) {
        this.app = app;
    }

    public JFrame getFrame() {
        return this.app.getFrame();
    }

    public void exitApp() {
        app.exitApp();
    }

    public int getCurrentAppHeight() {
        return app.getFrameHeight();
    }

    public void popupMessage(String message) {
        JOptionPane.showMessageDialog(getFrame(), message);
    }


}
