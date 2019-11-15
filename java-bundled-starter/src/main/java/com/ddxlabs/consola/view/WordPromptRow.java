package com.ddxlabs.consola.view;

import com.ddxlabs.consola.UserPreferences;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Collection;

/**
 *  Row that sits above the Command Input, used to hint at the possible words to enter.
 *
 *  Rules:
 *  = only prompts if the word fragment is at least 3 characters
 *  - character upper limit for row (if number of possible words cause overflow, then don't display)
 *  - as the user types into the command input field, the number of words decreases, and this row is refreshed
 *  - after a space is entered, the word list resets
 *
 */
public class WordPromptRow implements ViewComponent {

    private static int MAX_CHARACTERS = 80;

    private JLabel wordsField;

    private TextTheme currentTheme;

    private static final float FONT_DROP_PCT = 0.8f;

    public WordPromptRow(UserPreferences defaultPrefs) {
        this.currentTheme = new TextTheme(defaultPrefs);
    }

    @Override
    public JComponent buildUI() {
        JPanel outerPanel = new JPanel(new BorderLayout());
        int pad = currentTheme.getBasePadding();
        outerPanel.setBorder(new EmptyBorder(pad, pad, 0, pad));

        wordsField = new JLabel("***");
        wordsField.setOpaque(true);
        wordsField.setBorder(BorderFactory.createCompoundBorder(
                wordsField.getBorder(),
                BorderFactory.createEmptyBorder(pad, pad, pad, 0)));

        refreshFont();
        refreshTheme();

        outerPanel.add(wordsField);

        return outerPanel;
    }

    @Override
    public void applyPreferences(UserPreferences preferences) {
        this.currentTheme.setFromPreferences(preferences);
        this.refreshTheme();
        this.refreshFont();
    }

    private void refreshTheme() {
        wordsField.setBackground(this.currentTheme.getColorTheme().bgColor);
    }

    private void refreshFont() {
        int fontSize = Math.round((currentTheme.getBaseFontSize() * FONT_DROP_PCT * 2 ) / 2);
        Font inputFont = new Font(currentTheme.getFontFamily(), Font.PLAIN, fontSize);
        wordsField.setFont(inputFont);
    }

    public void updateWords(Collection<String> wordsList) {
        if (wordsList!=null && !wordsList.isEmpty()) {
            wordsField.setText(String.join(" ", wordsList));
            wordsField.setForeground(currentTheme.getColorTheme().sysColor);
        } else {
            wordsField.setText("***");
            // hide the text
            wordsField.setForeground(currentTheme.getColorTheme().bgColor);
        }
    }

}
