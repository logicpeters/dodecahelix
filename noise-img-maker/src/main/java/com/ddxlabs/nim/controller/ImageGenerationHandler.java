package com.ddxlabs.nim.controller;

import com.ddxlabs.nim.Controllers;
import com.ddxlabs.nim.Models;
import com.ddxlabs.nim.UserPreferences;
import com.ddxlabs.nim.Views;
import com.ddxlabs.nim.noise.ImageGenerator;

import com.ddxlabs.nim.noise.NmBuilder;
import org.spongepowered.noise.module.Module;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageGenerationHandler implements ControllerComponent {

    private final UserPreferences prefs;
    private NmBuilder moduleBuilder;

    private JFrame reusableFrame;
    private JLabel imageLabel;

    public ImageGenerationHandler(UserPreferences prefs) {
        this.prefs = prefs;
    }

    public void init(Controllers controllers, Views views, Models models) {
        this.moduleBuilder = models.getNmBuilder();
    }

    public void generateAndShowImage() {
        Module module = moduleBuilder.build();
        String imageFolder = prefs.getPreference(UserPreferences.KEY_IMAGE_FOLDER) + System.getProperty("file.separator");
        String imageFilePath = imageFolder + moduleBuilder.getStructure().getRootModuleId() + ".png";
        // TODO - get all these params from UserPrefs
        ImageGenerator.generateImage(module,
                true, 5, imageFilePath,
                1024, 1024, 128, 128, 0, 5000);
        try {
            popupImage(imageFilePath, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void popupImage(String imagePath, boolean reuseFrame) throws IOException {
        JFrame f;
        ImageIcon image = new ImageIcon(ImageIO.read(new File(imagePath)));

        if (reuseFrame && (reusableFrame != null) && reusableFrame.isVisible()) {
            f = reusableFrame;
            imageLabel.setIcon(image);
        } else {
            f = new JFrame();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            f.setUndecorated(true);
            f.setSize(image.getIconWidth(), image.getIconHeight());
            int x = (screenSize.width - f.getSize().width)/2;
            int y = (screenSize.height - f.getSize().height)/2;
            f.setLocation(x, y);
            f.setVisible(true);
            imageLabel = new JLabel(image);
            f.getContentPane().add(imageLabel);
            reusableFrame = f;
        }
    }

}
