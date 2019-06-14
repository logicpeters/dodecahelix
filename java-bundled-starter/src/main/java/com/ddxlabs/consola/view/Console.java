package com.ddxlabs.consola.view;

import com.ddxlabs.consola.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created on 5/30/2019.
 */
public class Console implements ViewComponent {

    private Application app;

    private JTextPane textPane;
    private StyledDocument doc;

    // lengths of the lines that are present in the styled document
    private Queue<Integer> linesAdded;

    // number of lines to retain in the history
    private int maxLineHistory;

    private int currentFontSize = 12;

    // whether the current theme is 'dark' - this will switch colors
    private ColorTheme currentTheme = ColorTheme.STD_LIGHT;

    public Console(Application app, int maxLineHistory) {
        this.app = app;
        this.linesAdded = new ArrayDeque<>();
        this.maxLineHistory = maxLineHistory;
    }

    public JComponent buildUI() {
        // build the app
        textPane = new JTextPane();
        doc = textPane.getStyledDocument();
        textPane.setEditable(false);

        int innerPadding = 5;
        textPane.setBorder(new EmptyBorder(innerPadding, innerPadding, innerPadding, innerPadding));

        addStylesToDocument(doc);

        setTheme(Menu.THEME_STD_LIGHT);
        return new JScrollPane(textPane);
    }

    public void addLine(StyledLine line) throws BadLocationException {

        // if past the line limit, remove the top line
        if (linesAdded.size() == maxLineHistory) {
            // add 1 for the newline
            int headLineSize = linesAdded.remove() + 1;
            try {
                doc.remove(0, headLineSize);
            } catch (BadLocationException e) {
                // just leave this??
                e.printStackTrace();
            }
        }

        for (StyledFragment fragment : line.getFragments()) {
            doc.insertString(doc.getLength(),
                    fragment.getText(),
                    doc.getStyle(fragment.getStyle().name()));
        }

        // add newline
        doc.insertString(doc.getLength(),
                Constants.NEWLINE,
                doc.getStyle(TextStyle.REGULAR.name()));

    }

    /**
     * Updates the color scheme.
     * <p>
     * There are two types - light and dark themes.
     *
     * @param themeId
     */
    public void setTheme(String themeId) {
        ColorTheme theme = ColorTheme.forId(themeId);
        textPane.setBackground(theme.bgColor);
        textPane.setForeground(theme.fgColor);

        // TODO - this is not working
        Style ss = doc.getStyle(TextStyle.MINIMAL.name());
        StyleConstants.setForeground(ss, theme.minColor);
        ss = doc.getStyle(TextStyle.SYSTEM.name());
        StyleConstants.setForeground(ss, theme.sysColor);
        ss = doc.getStyle(TextStyle.DIALOG.name());
        StyleConstants.setForeground(ss, theme.diaColor);

        this.currentTheme = theme;
    }


    public void incrementFont(int size) {
        int newFontSize = currentFontSize + 2 * size;
        if (newFontSize >= 8 && newFontSize <= 24) {
            currentFontSize = newFontSize;
            Style sans = doc.getStyle("sans");
            StyleConstants.setFontSize(sans, currentFontSize);
        }
    }

    private void addStylesToDocument(StyledDocument doc) {
        //Initialize some styles.
        Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        Style sans = doc.addStyle("sans", def);
        StyleConstants.setFontFamily(sans, "Courier New");

        doc.addStyle(TextStyle.REGULAR.name(), sans);

        Style style;
        style = doc.addStyle(TextStyle.ITALIC.name(), sans);
        StyleConstants.setItalic(style, true);

        style = doc.addStyle(TextStyle.BOLD.name(), sans);
        StyleConstants.setBold(style, true);

        style = doc.addStyle(TextStyle.MINIMAL.name(), sans);
        StyleConstants.setForeground(style, currentTheme.minColor);

        style = doc.addStyle(TextStyle.DIALOG.name(), sans);
        StyleConstants.setForeground(style, currentTheme.diaColor);

        style = doc.addStyle(TextStyle.SYSTEM.name(), sans);
        StyleConstants.setForeground(style, currentTheme.sysColor);
    }


}
