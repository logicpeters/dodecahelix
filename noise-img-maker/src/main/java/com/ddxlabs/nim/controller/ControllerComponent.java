package com.ddxlabs.nim.controller;

import com.ddxlabs.nim.Controllers;
import com.ddxlabs.nim.Models;
import com.ddxlabs.nim.Views;

public interface ControllerComponent {

    /**
     * Wire in associations to MVC components
     *
     * @param controllers
     * @param views
     * @param models
     */
    public void init(Controllers controllers, Views views, Models models);

}
