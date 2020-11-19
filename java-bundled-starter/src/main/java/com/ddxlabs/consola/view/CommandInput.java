package com.ddxlabs.consola.view;

import com.ddxlabs.consola.command.AutofillHandler;
import com.ddxlabs.consola.command.CommandHandler;
import com.ddxlabs.consola.command.WordPromptHandler;
import com.ddxlabs.consola.prefs.Constants;
import com.ddxlabs.consola.prefs.UserPreferences;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created on 5/30/2019.
 */
public class CommandInput implements ViewComponent {

    private static final int MAX_HISTORY_COMMANDS = 50;

    /**
     * Used to process the full command when enter is pressed.
     */
    private CommandHandler commandHandler;

    /**
     * Processes the current input for any key entry.
     */
    private WordPromptHandler wordPromptHandler;

    /**
     *  When a tab is pressed, attempt autofill.
     */
    private AutofillHandler autofillHandler;

    private JTextField inputField;
    private JComboBox<String> subjectDropdown;

    /**
     * Maintain a list of commands that you can navigate back to using the arrow keys.
     */
    private LinkedList<String> commandHistory;
    private int backPointer = 0;

    private String defaultSubject = Constants.DEFAULT_SUBJECT;

    public CommandInput(UserPreferences defaultPrefs,
                        CommandHandler commandHandler,
                        WordPromptHandler wordPromptHandler,
                        AutofillHandler autofillHandler) {

        this.commandHandler = commandHandler;
        this.wordPromptHandler = wordPromptHandler;
        this.commandHistory = new LinkedList<>();
        this.autofillHandler = autofillHandler;
    }

    public JComponent buildUI() {
        JPanel outerPanel = new JPanel(new BorderLayout());
        int innerPadding = 5;
        outerPanel.setBorder(new EmptyBorder(innerPadding, innerPadding, innerPadding, innerPadding));

        inputField = new JTextField();
        inputField.setBorder(BorderFactory.createCompoundBorder(
                inputField.getBorder(),
                BorderFactory.createEmptyBorder(innerPadding, innerPadding, innerPadding, innerPadding)));
        Font inputFont = new Font("Courier New", Font.PLAIN, 18);
        inputField.setFont(inputFont);
        inputField.setCaret(new SolidCaret());

        // tabbing should be used for autofill, not focus
        inputField.setFocusTraversalKeysEnabled(false);

        inputField.addActionListener(e -> {
            String input = inputField.getText();

            // maintain a history of command entries for up/down arrows
            if (commandHistory.size() >= MAX_HISTORY_COMMANDS) {
                commandHistory.removeFirst();
            }
            commandHistory.add(input);

            // reset the backPointer
            backPointer = commandHistory.size();  // points to the head of the index

            CommandInput.this.commandHandler.processCommand((String) subjectDropdown.getSelectedItem(), input);
            inputField.setText("");
        });

        inputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                wordPromptHandler.processInput(inputField.getText());

                int keyCode = keyEvent.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        // go back into history and pull up the last command
                        if (backPointer > 0) {
                            inputField.setText(commandHistory.get(backPointer - 1));
                            backPointer--;
                            if (backPointer < 1) {
                                // dont go past the first element
                                backPointer = 1;
                            }
                        }
                        break;

                    case KeyEvent.VK_DOWN:
                        // go forward in history
                        if (backPointer < commandHistory.size()) {
                            // only works if we've gone back
                            inputField.setText(commandHistory.get(backPointer));
                            backPointer++;
                        } else {
                            inputField.setText("");
                        }
                        break;

                    case KeyEvent.VK_TAB:
                        autofillHandler.autofill();
                        break;
                }
            }
        });

        // TODO - load values from possible subjects
        String[] subjectOptions = {this.defaultSubject};
        subjectDropdown = new JComboBox<String>(subjectOptions);
        subjectDropdown.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> jList, Object value, int idx, boolean isSelected, boolean cellHasFocus) {
                JComponent comp = (JComponent) super.getListCellRendererComponent(jList, value, idx, isSelected, cellHasFocus);
                comp.setBorder(new EmptyBorder(3, 3, 3, 3));
                return comp;
            }
        });
        subjectDropdown.setFont(inputFont);

        outerPanel.add(inputField, BorderLayout.CENTER);
        if (Constants.SUBJECT_OPTIONS_ENABLED) {
            outerPanel.add(subjectDropdown, BorderLayout.WEST);
        }
        return outerPanel;
    }

    public void autofill(String word) {
        String currentCommand = inputField.getText();

        int i = currentCommand.lastIndexOf(" ");
        if (i > 0) {
            // only interested in the last word fragment of the input
            inputField.setText(currentCommand.substring(0, i) + " " + word);
        } else {
            inputField.setText(word);
        }

        // clear word prompts
        wordPromptHandler.processInput("*");
    }

    @Override
    public void applyPreferences(UserPreferences preferences) {

    }

    public void setFocus() {
        inputField.requestFocus();
    }

    public void updateSubjects(Collection<String> subjects) {
        subjectDropdown.removeAllItems();
        if (!subjects.isEmpty()) {
            subjects.stream().forEach(subject -> subjectDropdown.addItem(subject));
        } else {
            // no subjects, use default
            subjectDropdown.addItem(this.defaultSubject);
        }
    }
}
