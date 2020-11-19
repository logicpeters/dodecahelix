package com.ddxlabs.consola;

import com.ddxlabs.consola.command.AutofillHandler;
import com.ddxlabs.consola.command.CommandHandler;
import com.ddxlabs.consola.command.WordPromptHandler;
import com.ddxlabs.consola.ext.EchoCommandHandler;
import com.ddxlabs.consola.prefs.UserPreferences;
import com.ddxlabs.consola.prefs.UserPreferencesHandler;
import com.ddxlabs.consola.response.StyledLine;
import com.ddxlabs.consola.response.TextStyle;
import com.ddxlabs.consola.ext.StandardMenuItemHandler;
import com.ddxlabs.consola.view.*;
import com.ddxlabs.consola.view.Menu;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class Application implements Runnable, ViewContainer {

    protected JFrame frame;

    // view components
    protected Console console;
    protected CommandInput commandInput;
    protected WordPromptRow wordPromptRow;
    protected Menu menu;

    // control components
    private CommandHandler commandHandler;
    private WordPromptHandler wordPromptHandler;
    private MenuItemHandler menuItemHandler;
    private UserPreferencesHandler userPreferencesHandler;
    private AutofillHandler autofillHandler;
    private ExitHandler exitHandler;

    public Application() {
        // initialize preferences with defaults
        UserPreferences defaultPrefs = new UserPreferences();

        // initialize handlers for the various backends
        //   handlers can call app for views and other handlers
        this.exitHandler = new ExitHandler(this);
        this.wordPromptHandler = new WordPromptHandler(this);
        this.commandHandler = new EchoCommandHandler(this, wordPromptHandler, exitHandler);
        this.userPreferencesHandler = new UserPreferencesHandler(defaultPrefs);
        this.menuItemHandler = new StandardMenuItemHandler(this, userPreferencesHandler, exitHandler);
        this.autofillHandler = new AutofillHandler(this, wordPromptHandler);

        // initialize the view components that make up the GUI for this application
        //   views can reference handlers, but not app or other views directly
        //   pass in the default userPreferences (read-only methods)
        this.console = new Console(defaultPrefs);
        this.commandInput = new CommandInput(defaultPrefs, commandHandler, wordPromptHandler, autofillHandler);
        this.menu = new Menu(defaultPrefs, menuItemHandler);
        this.wordPromptRow = new WordPromptRow(defaultPrefs);
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
            quoteLine.addFragment("'Never give a sword to a man who can't dance'", TextStyle.DIALOG);
            quoteLine.addFragment(" - Confucius", TextStyle.ITALIC);
            console.addLine(quoteLine);

            console.addLine(new StyledLine("This is BIG!", TextStyle.LARGE));
            console.addLine(new StyledLine("This is tiny!", TextStyle.SMALL));
            console.addLine(new StyledLine("Enter 'exit' to close the application.", TextStyle.SYSTEM));
            console.addLine(new StyledLine(""));

        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        // should the following happen in the buildUI section before setVisible?
        UserPreferences loadedPrefs = userPreferencesHandler.loadPreferences();
        refreshViews(loadedPrefs);
    }

    protected void buildUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);

        frame = new JFrame("Consola");

        frame.setIconImage(IconUtils.getIcon("tetra").getImage());
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

        JPanel southComponents = new JPanel(new BorderLayout());
        southComponents.add(wordPromptRow.buildUI(), BorderLayout.CENTER);
        southComponents.add(commandInput.buildUI(), BorderLayout.SOUTH);
        frame.add(southComponents, BorderLayout.SOUTH);

        frame.setSize(800, 640);
        frame.setVisible(true);
    }

    private void refreshViews(UserPreferences loadedPrefs) {
        allViews().forEach(view -> view.applyPreferences(loadedPrefs));
    }

    public Console getConsole() {
        return console;
    }

    public CommandInput getCommandInput() {
        return commandInput;
    }

    public WordPromptRow getWordPromptRow() {
        return wordPromptRow;
    }

    public Menu getMenu() {
        return menu;
    }

    public List<ViewComponent> allViews() {
        List<ViewComponent> views = new ArrayList<>();
        views.add(console);
        views.add(commandInput);
        views.add(wordPromptRow);
        views.add(menu);
        return views;
    }

    public void disposeUI() {
        frame.dispose();
    }
}
