package com.ddxlabs.consola.system;

import com.ddxlabs.consola.Application;
import com.ddxlabs.consola.view.IconUtils;
import com.ddxlabs.consola.view.Grid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GridApplication extends Application {

    private Grid grid;

    public GridApplication() {
        super();

        this.grid = new Grid(this, 8, 8);
    }

    @Override
    protected void buildUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("Consola (Grid)");

        frame.setIconImage(IconUtils.getIcon("bluedot").getImage());
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
        frame.add(grid.buildUI(), BorderLayout.WEST);
        frame.add(console.buildUI(), BorderLayout.CENTER);
        frame.add(commandInput.buildUI(), BorderLayout.SOUTH);

        //JPanel southComponents = new JPanel(new BorderLayout());
        //southComponents.add(wordPromptRow.buildUI(), BorderLayout.CENTER);
        //southComponents.add(commandInput.buildUI(), BorderLayout.SOUTH);
        //frame.add(southComponents, BorderLayout.SOUTH);
        //wordPromptRow.updateWords(Arrays.asList("hello", "world"));

        frame.setSize(800, 640);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new GridApplication());
    }
}
