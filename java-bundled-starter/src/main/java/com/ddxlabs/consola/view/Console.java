package com.ddxlabs.consola.view;

import com.ddxlabs.consola.*;
import com.ddxlabs.consola.response.StyledFragment;
import com.ddxlabs.consola.response.StyledLine;
import com.ddxlabs.consola.response.TextStyle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created on 5/30/2019.
 */
public class Console implements ViewComponent {

    public static final String BASE_STYLE = "base";

    private JTextPane textPane;
    private StyledDocument doc;

    // lengths of the lines that are present in the styled document
    private Queue<Integer> linesAdded;

    // number of lines to retain in the history
    private int maxLineHistory;

    private TextTheme textTheme;

    public Console(UserPreferences defaultPrefs) {
        this.linesAdded = new ArrayDeque<>();

        this.textTheme = new TextTheme(defaultPrefs);
        this.maxLineHistory = defaultPrefs.getIntPreference(UserPreferences.KEY_MAX_HISTORY_LINES);
    }

    public JComponent buildUI() {
        // build the app
        textPane = new JTextPane();
        doc = textPane.getStyledDocument();
        textPane.setEditable(false);

        int pad = textTheme.getBasePadding();
        textPane.setBorder(new EmptyBorder(pad, pad, pad, pad));

        addStylesToDocument(doc);

        // set the default color schemes
        this.refreshColorTheme();
        return new JScrollPane(textPane);
    }

    @Override
    public void applyPreferences(UserPreferences preferences) {
        this.textTheme.setFromPreferences(preferences);
        this.maxLineHistory = preferences.getIntPreference(UserPreferences.KEY_MAX_HISTORY_LINES);

        this.refreshColorTheme();
        this.refreshFontSizes();
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
     * Updates the color scheme based on the currently loaded textTheme.
     *
     */
    private void refreshColorTheme() {
        ColorTheme colors = textTheme.getColorTheme();
        textPane.setBackground(colors.bgColor);
        textPane.setForeground(colors.fgColor);

        Style ss = doc.getStyle(TextStyle.MINIMAL.name());
        StyleConstants.setForeground(ss, colors.minColor);
        updateStyle(ss);
        ss = doc.getStyle(TextStyle.SYSTEM.name());
        StyleConstants.setForeground(ss, colors.sysColor);
        updateStyle(ss);
        ss = doc.getStyle(TextStyle.DIALOG.name());
        StyleConstants.setForeground(ss, colors.diaColor);
        updateStyle(ss);
    }

    private void refreshFontSizes() {
        int newFontSize = textTheme.getBaseFontSize();

        Style textStyle = doc.getStyle(BASE_STYLE);
        StyleConstants.setFontSize(textStyle, newFontSize);

        int smallFontSize = (int) (newFontSize * 0.75);
        if (smallFontSize % 2 != 0) {
            smallFontSize++;
        }
        textStyle = doc.getStyle(TextStyle.SMALL.name());
        StyleConstants.setFontSize(textStyle, smallFontSize);
        updateStyle(textStyle);

        int largeFontSize = (int) (newFontSize * 1.25);
        if (largeFontSize % 2 != 0) {
            largeFontSize++;
        }
        textStyle = doc.getStyle(TextStyle.LARGE.name());
        StyleConstants.setFontSize(textStyle, largeFontSize);
        updateStyle(textStyle);
    }

    private void addStylesToDocument(StyledDocument doc) {
        //Initialize some styles.
        Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        Style baseStyle = doc.addStyle(BASE_STYLE, def);
        StyleConstants.setFontFamily(baseStyle, textTheme.getFontFamily());
        StyleConstants.setFontSize(baseStyle, textTheme.getBaseFontSize());

        doc.addStyle(TextStyle.REGULAR.name(), baseStyle);

        Style style = doc.addStyle(TextStyle.ITALIC.name(), baseStyle);
        StyleConstants.setItalic(style, true);

        style = doc.addStyle(TextStyle.BOLD.name(), baseStyle);
        StyleConstants.setBold(style, true);

        style = doc.addStyle(TextStyle.SMALL.name(), baseStyle);
        StyleConstants.setFontSize(style, 10);

        style = doc.addStyle(TextStyle.LARGE.name(), baseStyle);
        StyleConstants.setFontSize(style, 18);

        style = doc.addStyle(TextStyle.MINIMAL.name(), baseStyle);
        StyleConstants.setForeground(style, textTheme.getColorTheme().minColor);

        style = doc.addStyle(TextStyle.DIALOG.name(), baseStyle);
        StyleConstants.setForeground(style, textTheme.getColorTheme().diaColor);

        style = doc.addStyle(TextStyle.SYSTEM.name(), baseStyle);
        StyleConstants.setForeground(style, textTheme.getColorTheme().sysColor);
    }

    /**
     * Refresh all elements of this doc with the new style
     *
     * @param updatedStyle
     */
    private void updateStyle(AttributeSet updatedStyle) {

        Object styleName = updatedStyle.getAttribute(AttributeSet.NameAttribute);

        ElementIterator i = new ElementIterator(doc);
        for (Element e = i.first(); e != null; e = i.next()) {
            AttributeSet attr = e.getAttributes();
            Object name = attr.getAttribute(AttributeSet.NameAttribute);
            if (styleName.equals(name)) {
                int start = e.getStartOffset();
                int end = e.getEndOffset();
                doc.setCharacterAttributes(start, end - start,
                        updatedStyle, false);
            }
        }
    }

}
