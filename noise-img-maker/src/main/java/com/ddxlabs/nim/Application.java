package com.ddxlabs.nim;

import com.ddxlabs.nim.controller.Controllers;
import com.ddxlabs.nim.noise.Models;
import com.ddxlabs.nim.utils.IconUtils;
import com.ddxlabs.nim.utils.ScreenUtils;
import com.ddxlabs.nim.noise.NmBuilder;
import com.ddxlabs.nim.noise.NmType;
import com.ddxlabs.nim.view.Views;

import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;

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
        controllers.init(views, models);

        // link the views to controllers
        views.init(controllers);
    }

    public static void main(String[] args) {
        String slaf = "com.jtattoo.plaf.smart.SmartLookAndFeel";
        String alaf = "com.jtattoo.plaf.acryl.AcrylLookAndFeel";
        String aelaf = "com.jtattoo.plaf.aero.AeroLookAndFeel";
        String hflaf = "com.jtattoo.plaf.hifi.HiFiLookAndFeel";
        String flaf = "com.jtattoo.plaf.fast.FastLookAndFeel";
        String mlaf = "com.jtattoo.plaf.mint.MintLookAndFeel";
        String llaf = "com.jtattoo.plaf.luna.LunaLookAndFeel";
        String mcwinLaf = "com.jtattoo.plaf.mcwin.McWinLookAndFeel";
        String alumLaf = "com.jtattoo.plaf.aluminium.AluminiumLookAndFeel";
        try {
            UIManager.setLookAndFeel(flaf);
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(new Application());
    }

    public void run() {
        this.controllers.getSystemHandler().setApp(this);
        buildUI();

        // create a single SOURCE structure as a beginning
        // TODO - this will probably go away
        NmBuilder builder = models.getNmBuilder();
        builder.build();
        String rootModuleId = builder.getStructure().getRootModuleId();
        views.getModuleTabs().addModule(NmType.SOURCE, rootModuleId);
        views.getGeneratorRow().setBaseSeed(builder.getBaseSeed());

        controllers.getImageGenerationHandler().generateAndShowImage();
    }

    protected void buildUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);

        frame = new JFrame("Noise Image Maker");
        try {
            frame.setIconImage(IconUtils.getIcon("bluedot.png").getImage());
        } catch (IOException e) {
            // if you cant load the icon -- no biggie
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

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(views.getModuleTabs().buildUI(), BorderLayout.CENTER);
        leftPanel.add(views.getGeneratorRow().buildUI(), BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(views.getImageView().buildUI(), BorderLayout.CENTER);

        JPanel framePanel = new JPanel(new GridLayout(1,2));
        framePanel.add(leftPanel);
        framePanel.add(rightPanel);

        frame.add(framePanel, BorderLayout.CENTER);

        int recommendedSquareSize = ScreenUtils.recommendSquareSize(128, 0.8);
        int appWidth = 2 * recommendedSquareSize + 20;
        int appHeight = recommendedSquareSize + 50;
        frame.setSize(appWidth, appHeight);
        frame.setLocation(25, 25);
        frame.setVisible(true);
    }

    public void exitApp() {
        frame.dispose();
    }

    public int getFrameHeight() {
        Dimension contentPaneSize = frame.getContentPane().getSize();
        return (int)contentPaneSize.getHeight();
    }

    public JFrame getFrame() {
        return frame;
    }
}
