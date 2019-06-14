package com.ddxlabs.consola;

/**
 * Created on 6/1/2019.
 */
public class StyledFragment {

    String text;
    TextStyle style;

    public StyledFragment(String text) {
        this.text = text;
        this.style = TextStyle.REGULAR;
    }

    public StyledFragment(String text, TextStyle style) {
        this(text);
        this.style = style;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TextStyle getStyle() {
        return style;
    }

    public void setStyle(TextStyle style) {
        this.style = style;
    }
}
