package com.ddxlabs.nim.controller;

import com.ddxlabs.nim.noise.Models;
import com.ddxlabs.nim.UserPreferences;
import com.ddxlabs.nim.view.Views;

/**
 *  Manage the state of UserPreferences
 *
 *  Write to disk when a preference changes.
 *
 */
public class UserPreferencesHandler implements ControllerComponent {

    private UserPreferences prefs;

    public UserPreferencesHandler(UserPreferences prefs) {
        this.prefs = prefs;
    }

    public void init(Controllers controllers, Views views, Models models) {

    }

    public UserPreferences getPrefs() {
        return prefs;
    }

    public void setPreference(String key, String value) {
        prefs.setPreference(key, value);

        // when a preference is updated, persist to disk
        prefs.savePreferences();
    }

}
