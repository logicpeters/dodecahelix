package com.ddxlabs.nim;

public enum Preset {

    SIMPLE ("simple perlin", "/presets/simple-perlin-with-chop.nim"),
    RAINBOW ("rainbow", "/presets/rainbow-chop.nim"),
    MAPS ("islands map", "/presets/maps-chop-1.nim"),
    BLENDED ("blended red", "/presets/blended-red.nim"),
    ;

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
