package com.ddxlabs.consola.view;

import com.ddxlabs.consola.Application;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created on 5/30/2019.
 */
public class CommandInput implements ViewComponent {

    private Application app;
    private JTextField inputField;
    private JLabel commandLabel;

    public CommandInput(Application app) {
        this.app = app;
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
        inputField.addActionListener(e -> {
            String input = inputField.getText();
            CommandInput.this.app.processCommand(input);
            inputField.setText("");
        });


        commandLabel = new JLabel("Command > ");
        commandLabel.setFont(inputFont);

        outerPanel.add(inputField, BorderLayout.CENTER);
        outerPanel.add(commandLabel, BorderLayout.WEST);
        return outerPanel;
    }

    public void setFocus() {
        inputField.requestFocus();
    }

}
