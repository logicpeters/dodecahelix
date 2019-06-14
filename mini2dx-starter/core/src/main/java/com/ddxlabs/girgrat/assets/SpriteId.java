package com.ddxlabs.girgrat.assets;

/**
 * Created on 2/15/2017.
 */
public enum SpriteId {

    RED_CUBE ("sprites/square_red.png"),
    ALIEN_BLUE ("sprites/alien_blue.png"),
    ALIEN_RED ("sprites/alien_pink.png"),
    ALIEN_YELLOW ("sprites/alien_yellow.png"),
    ALIEN_GREEN ("sprites/alien_green.png"),
    ALIEN_BEIGE ("sprites/alien_beige.png"),
    ;

    private String filePath;

    SpriteId(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
