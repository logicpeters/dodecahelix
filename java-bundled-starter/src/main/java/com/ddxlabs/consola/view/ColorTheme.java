package com.ddxlabs.consola.view;

import java.awt.*;

public enum ColorTheme {

    STD_LIGHT (Color.WHITE, Color.BLACK, Color.RED, Color.BLUE, Color.DARK_GRAY),
    STD_DARK (Color.BLACK, Color.WHITE, Color.PINK, Color.CYAN, Color.LIGHT_GRAY),
    OCEAN_DARK (new Color(11, 48, 111), Color.WHITE, Color.PINK, Color.CYAN, Color.LIGHT_GRAY);

    Color bgColor;
    Color fgColor;
    Color sysColor; // system messages (i.e; errors)
    Color diaColor; // dialogue messages (i.e; chatter)
    Color minColor; // inconspicuous messages (i.e; passive logs)

    ColorTheme(Color bgColor, Color fgColor, Color sysColor, Color diaColor, Color minColor) {
        this.bgColor = bgColor;
        this.fgColor = fgColor;
        this.sysColor = sysColor;
        this.diaColor = diaColor;
        this.minColor = minColor;
    }

    public static ColorTheme forId(String themeId) {
        themeId = themeId.substring(6).toUpperCase();
        for (ColorTheme theme: values()) {
            if (theme.name().equals(themeId)) {
                return theme;
            }
        }
        System.err.println("No theme found for id " + themeId);
        return STD_LIGHT;
    }

}
