package com.ddxlabs.consola.view;

import com.ddxlabs.consola.prefs.UserPreferences;

public class TextTheme {

    private ColorTheme colorTheme;
    private String fontFamily;
    private int baseFontSize;
    private int basePadding;

    public TextTheme(ColorTheme colorTheme, String fontFamily, int baseFontSize, int basePadding) {
        this.colorTheme = colorTheme;
        this.fontFamily = fontFamily;
        this.baseFontSize = baseFontSize;
        this.basePadding = basePadding;
    }

    public TextTheme(UserPreferences prefs) {
        setFromPreferences(prefs);
    }

    public void setFromPreferences(UserPreferences prefs) {
        this.baseFontSize = prefs.getIntPreference(UserPreferences.KEY_BASE_FONT_SIZE);
        this.basePadding = prefs.getIntPreference(UserPreferences.KEY_BASE_PADDING);
        this.colorTheme = prefs.getColorTheme();
        this.fontFamily = prefs.getPreference(UserPreferences.KEY_FONT_FAMILY);
    }

    public ColorTheme getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(ColorTheme colorTheme) {
        this.colorTheme = colorTheme;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public int getBaseFontSize() {
        return baseFontSize;
    }

    public void setBaseFontSize(int baseFontSize) {
        this.baseFontSize = baseFontSize;
    }

    public int getBasePadding() {
        return basePadding;
    }

    public void setBasePadding(int basePadding) {
        this.basePadding = basePadding;
    }
}
