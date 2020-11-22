package com.ddxlabs.nim.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *  Image label that scales the internal image to fit inside the JPanel.
 */
public class ScaledImageLabel extends JLabel {

    private Image image;

    public ScaledImageLabel(String displayText, int alignment) {
        super(displayText, alignment);
    }

    public void setImage(BufferedImage newImage) {
        ImageIcon icon = new ImageIcon(newImage);
        super.setIcon(icon);
        image = newImage;
    }

    @Override
    public void paint(Graphics g){
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
    }
}
