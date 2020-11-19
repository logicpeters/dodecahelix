package com.ddxlabs.consola.view;

import com.ddxlabs.consola.Application;
import com.ddxlabs.consola.prefs.UserPreferences;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Grid implements ViewComponent {

    // "empty" border size for grid itself
    private static final int GPAD = 5;

    // "empty" border size for the labels around the grid
    private static final int LPAD = 2;

    private Application app;

    private JPanel gridPanel;

    private int rows;
    private int columns;

    public Grid(Application app, int numColumns, int numRows) {
        this.app = app;
        this.rows = numRows;
        this.columns = numColumns;
    }

    @Override
    public JComponent buildUI() {
        gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setBorder(BorderFactory.createEmptyBorder(GPAD, GPAD, GPAD, GPAD));

        GridBagConstraints headerConstraints = new GridBagConstraints();
        headerConstraints.insets = new Insets(LPAD, LPAD, LPAD, LPAD);

        GridBagConstraints cellConstraints = new GridBagConstraints();

        String header = "";
        for (int x=0; x<=columns+1; x++) {
            header = "";
            for (int y=0; y<=rows+1; y++) {
                if (y == 0 || y == (rows + 1)) {
                    // column labels
                    if (x > 0) {
                        header = String.format("X%d", x);
                    }
                    if (x==0 || x==columns+1) {
                        header = "";
                    }
                    headerConstraints.gridx = x;
                    headerConstraints.gridy = y;
                    gridPanel.add(new JLabel(header), headerConstraints);
                } else if (x == 0 || x == (columns + 1)) {
                    // row labels
                    if (y > 0) {
                        header = String.format("Y%d", y);
                    }
                    if (y==0 || y==rows+1) {
                        header = "";
                    }
                    headerConstraints.gridx = x;
                    headerConstraints.gridy = y;
                    gridPanel.add(new JLabel(header), headerConstraints);
                } else {
                    // cell
                    cellConstraints.gridx = x;
                    cellConstraints.gridy = y;
                    //ImageIcon icon = Utils.getIcon("aqua");
                    cellConstraints.insets = new Insets(1,1,1,1);

                    BufferedImage[][] sprites = IconUtils.getImagesFromSpritesheet("runes", 16,16, 32, 32);
                    ImageIcon icon = new ImageIcon(sprites[12][10]);

                    JLabel cell = new JLabel(icon);
                    //cell.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, Color.BLACK));
                    gridPanel.add(cell, cellConstraints);
                }
            }
        }

        return gridPanel;
    }

    @Override
    public void applyPreferences(UserPreferences preferences) {

    }
}
