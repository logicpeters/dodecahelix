package com.ddxlabs.nim;

public enum Preset {

    MAPS ("maps", "/presets/map-1.nim");

    String display;
    String path;

    Preset(String display, String path) {
        this.display = display;
        this.path = path;
    }

    public String getDisplay() {
        return display;
    }

    public String getPath() {
        return path;
    }
}
