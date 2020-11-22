package com.ddxlabs.nim.view;

import com.ddxlabs.nim.Controllers;
import com.ddxlabs.nim.UserPreferences;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePreviewView implements ViewComponent {

    private UserPreferences prefs;
    private JPanel imagePane;
    private JLabel imageLabel;

    public ImagePreviewView(UserPreferences prefs) {
        this.prefs = prefs;
    }

    @Override
    public void init(Controllers controllers) {
    }

    @Override
    public JComponent buildUI() {
        imagePane = new JPanel(new GridLayout(1,1));
        Border blackline = BorderFactory.createLineBorder(Color.black);
        imagePane.setBorder(blackline);

        //imageLabel = new JLabel("", SwingConstants.CENTER);
        imageLabel = new ScaledImageLabel("Preview (click button)", SwingConstants.CENTER);
        imagePane.add(imageLabel);

        imagePane.getInsets();
        return imagePane;
    }

    @Override
    public void applyPreferences() {
    }

    public void showImage(ImageIcon image) {
        imageLabel.setIcon(image);
    }

    public void previewImage(BufferedImage image) {
        ((ScaledImageLabel)imageLabel).setImage(image);
    }
}
