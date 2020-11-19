package com.ddxlabs.nim;

import com.ddxlabs.nim.io.IconUtils;
import com.ddxlabs.nim.noise.NmBuilder;
import com.ddxlabs.nim.noise.NmType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Application implements Runnable {

    protected JFrame frame;

    private Models models;
    private Views views;
    private Controllers controllers;

    public Application() {
        // initialize preferences with defaults, then load from disk (if possible)
        UserPreferences prefs = new UserPreferences();
        prefs.loadFromDisk();

        models = new Models(prefs);
        controllers = new Controllers(prefs);
        views = new Views(prefs);

        // build the controller components
        models.init();

        // link the controllers to views, models and the Application itself
        controllers.init(views, models, this);

        // link the views to controllers
        views.init(controllers);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Application());
    }

    public void run() {
        buildUI();

        // create a single SOURCE structure as a beginning
        // TODO - this will probably go away
        NmBuilder builder = models.getNmBuilder();
        builder.build();
        String rootModuleId = builder.getStructure().getRootModuleId();
        views.getModuleTabs().addModule(NmType.SOURCE, rootModuleId);
        views.getGeneratorRow().setBaseSeed(builder.getBaseSeed());
    }

    protected void buildUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);

        frame = new JFrame("Noise Image Maker");

        try {
            frame.setIconImage(IconUtils.getIcon("bluedot.png").getImage());
        } catch (IOException e) {
            // if you cant load the icon -- oh well
            e.printStackTrace();
        }
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener( new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
                // commandInput.setFocus();
            }
            public void windowClosed( WindowEvent e ){
                super.windowClosed(e);
                System.exit(0);
            }
        });

        frame.setJMenuBar(views.getMenu().buildUI());
        frame.add(views.getModuleTabs().buildUI(), BorderLayout.CENTER);
        frame.add(views.getGeneratorRow().buildUI(), BorderLayout.SOUTH);

        frame.setSize(800, 640);
        frame.setVisible(true);
    }

    public void exitApp() {
        frame.dispose();
    }

}
