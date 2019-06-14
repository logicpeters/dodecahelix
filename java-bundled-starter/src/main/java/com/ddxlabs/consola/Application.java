package com.ddxlabs.consola;

import com.ddxlabs.consola.system.SystemCommandHandler;
import com.ddxlabs.consola.view.CommandInput;
import com.ddxlabs.consola.view.Console;
import com.ddxlabs.consola.view.Menu;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application implements Runnable {

    private Console console;
    private CommandInput commandInput;
    private Menu menu;
    private JFrame frame;
    private CommandHandler commandHandler;

    public Application() {
        // Three view components make up the GUI for this application - console, menu and commandInput
        console = new Console(this, 100);
        commandInput = new CommandInput(this);
        menu = new Menu(this);

        this.commandHandler = new SystemCommandHandler();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Application());
    }

    public void run() {
        buildUI();
        try {
            StyledLine initialLine = new StyledLine();
            initialLine.addFragment("Welcome!", TextStyle.BOLD);
            initialLine.addFragment("  Consola app, version 1.0", TextStyle.REGULAR);
            console.addLine(initialLine);
            console.addLine(new StyledLine("styled text terminal", TextStyle.MINIMAL));
            StyledLine quoteLine = new StyledLine();
            quoteLine.addFragment("'Never give a sword to a man who can't Dance'", TextStyle.DIALOG);
            quoteLine.addFragment(" - Confucius", TextStyle.REGULAR);
            console.addLine(quoteLine);
            console.addLine(new StyledLine("Enter 'exit' to close the application.", TextStyle.SYSTEM));

        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void buildUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);

        frame = new JFrame("Consola");

        frame.setIconImage(Utils.getIcon("tetra").getImage());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener( new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
                commandInput.setFocus();
            }
            public void windowClosed( WindowEvent e ){
                super.windowClosed(e);
                System.exit(0);
            }
        });

        frame.setJMenuBar(menu.buildUI());
        frame.add(console.buildUI(), BorderLayout.CENTER);
        frame.add(commandInput.buildUI(), BorderLayout.SOUTH);

        frame.setSize(800, 640);
        frame.setVisible(true);
    }

    public void processCommand(String command) {
        if ("exit".equalsIgnoreCase(command)) {
            exitApp();
        }

        Response response = commandHandler.processCommand(command);
        for (StyledLine responseLine : response.getOutputDisplay()) {
            try {
                console.addLine(responseLine);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    public void processMenuItem(String menuItemId) {
        switch (menuItemId) {
            case Menu.APP_EXIT: exitApp(); break;
            case Menu.THEME_STD_DARK: console.setTheme(Menu.THEME_STD_DARK); break;
            case Menu.THEME_STD_LIGHT: console.setTheme(Menu.THEME_STD_LIGHT); break;
            case Menu.VIEW_INC_FONT: console.incrementFont(1); break;
            case Menu.VIEW_DEC_FONT: console.incrementFont(-1); break;
            default: System.err.println("Menu action " + menuItemId + " not implemented");
        }
    }

    public void exitApp() {
        frame.dispose();
    }

}
