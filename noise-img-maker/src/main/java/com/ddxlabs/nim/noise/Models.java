package com.ddxlabs.nim.noise;

import com.ddxlabs.nim.UserPreferences;
import com.ddxlabs.nim.noise.NmBuilder;

public class Models {

    private UserPreferences preferences;

    private NmBuilder nmBuilder;

    public Models(UserPreferences preferences) {
        this.preferences = preferences;
        this.nmBuilder = new NmBuilder();
    }

    public void init() {
    }

    public NmBuilder getNmBuilder() {
        return nmBuilder;
    }
}
