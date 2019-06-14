package org.ddxlabs.shell;

import com.googlecode.lanterna.TextColor;

import java.util.UUID;

public class StyledLine {

    private String content;
    private TextColor foreground;
    private TextColor background;
    private boolean bold;

    private long lineId;

    public StyledLine(String content) {
        this.content = content;
        this.bold = false;
        foreground = TextColor.ANSI.WHITE;
        background = TextColor.ANSI.BLACK;
        lineId = UUID.randomUUID().getLeastSignificantBits();
    }

    public StyledLine(String content, boolean bold) {
        this(content);
        this.bold = bold;
    }

    public StyledLine(String content, TextColor foreground, TextColor background, boolean bold) {
        this(content, bold);
        this.foreground = foreground;
        this.background = background;
    }

    public StyledLine(String content, TextColor foreground, TextColor background, boolean bold, long lineId) {
        this(content, foreground, background, bold);
        this.lineId = lineId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TextColor getForeground() {
        return foreground;
    }

    public void setForeground(TextColor foreground) {
        this.foreground = foreground;
    }

    public TextColor getBackground() {
        return background;
    }

    public void setBackground(TextColor background) {
        this.background = background;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public long getLineId() {
        return lineId;
    }

    public void setLineId(long lineId) {
        this.lineId = lineId;
    }
}
