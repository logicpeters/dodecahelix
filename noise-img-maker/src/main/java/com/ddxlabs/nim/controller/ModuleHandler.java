package com.ddxlabs.nim.controller;

import com.ddxlabs.nim.Controllers;
import com.ddxlabs.nim.Models;
import com.ddxlabs.nim.UserPreferences;
import com.ddxlabs.nim.Views;
import com.ddxlabs.nim.noise.NmBuilder;

public class ModuleHandler implements ControllerComponent {

    private NmBuilder moduleBuilder;
    private UserPreferences prefs;

    public ModuleHandler(Controllers controllers, Views views, Models models, UserPreferences prefs) {
        this.moduleBuilder = models.getNmBuilder();
        this.prefs = prefs;
    }


}
