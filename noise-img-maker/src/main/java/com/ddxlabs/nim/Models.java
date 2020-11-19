package com.ddxlabs.nim;

import com.ddxlabs.nim.noise.NmBuilder;

public class Models {

    private UserPreferences preferences;

    private NmBuilder nmBuilder;

    public Models(UserPreferences preferences) {
        this.preferences = preferences;
    }

    public void init() {
        nmBuilder = new NmBuilder();
    }

    public NmBuilder getNmBuilder() {
        return nmBuilder;
    }
}
