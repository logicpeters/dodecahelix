package com.ddxlabs.consola.view;

import com.ddxlabs.consola.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created on 5/30/2019.
 */
public class Menu implements ViewComponent {

    public static final String FILE_OPEN = "file_open";
    public static final String FILE_SAVE = "file_save";
    public static final String APP_EXIT = "app_exit";
    public static final String THEME_STD_DARK = "theme_std_dark";
    public static final String THEME_STD_LIGHT = "theme_std_light";
    public static final String HELP_ABOUT = "help_about";
    public static final String VIEW_INC_FONT = "view_inc_font";
    public static final String VIEW_DEC_FONT = "view_dec_font";
    private Application app;
    private JMenuBar menuBar;

    public Menu(Application app) {
        this.app = app;
    }

    public JMenuBar buildUI() {
        menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createLineBorder(Color.black));

        JMenu fileMenu = new JMenu("File");
        this.addMenuItem(fileMenu, "Open", FILE_OPEN, KeyEvent.VK_O);
        this.addMenuItem(fileMenu, "Save", FILE_SAVE, KeyEvent.VK_S);
        fileMenu.add(new JSeparator());
        this.addMenuItem(fileMenu, "Exit", APP_EXIT, 0);
        menuBar.add(fileMenu);

        JMenu viewMenu = new JMenu("View");
        JMenu themeMenu = new JMenu("Themes");
        viewMenu.add(themeMenu);
        this.addMenuItem(themeMenu, "Standard Dark", THEME_STD_DARK, 0);
        this.addMenuItem(themeMenu, "Standard Light", THEME_STD_LIGHT, 0);
        this.addMenuItem(viewMenu, "Increment Font Size", VIEW_INC_FONT, 0);
        this.addMenuItem(viewMenu, "Decrement Font Size", VIEW_DEC_FONT, 0);
        menuBar.add(viewMenu);

        JMenu helpMenu = new JMenu("Help");
        this.addMenuItem(helpMenu, "About", HELP_ABOUT, 0);
        menuBar.add(helpMenu);

        return menuBar;
    }

    private void addMenuItem(JMenu menu, String itemTitle, String itemId, int mnemonic) {
        JMenuItem menuItem = new JMenuItem(itemTitle);
        if (mnemonic>0) {
            menuItem.setMnemonic(mnemonic);
        }

        menuItem.addActionListener(e ->
            Menu.this.app.processMenuItem(itemId)
        );
        menu.add(menuItem);
    }


}
