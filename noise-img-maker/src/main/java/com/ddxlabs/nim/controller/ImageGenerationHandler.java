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

        boolean color = Boolean.valueOf(prefs.getPreference(UserPreferences.KEY_IMAGE_COLOR));
        int chop = prefs.getIntPreference(UserPreferences.KEY_IMAGE_CHOP);
        int size = prefs.getIntPreference(UserPreferences.KEY_IMAGE_PIXEL_SIZE);
        int period = prefs.getIntPreference(UserPreferences.KEY_IMAGE_PERIOD);
        int llkb = prefs.getIntPreference(UserPreferences.KEY_IMAGE_LOW_LIMIT_KB);
        int hlkb = prefs.getIntPreference(UserPreferences.KEY_IMAGE_HIGH_LIMIT_KB);

        ImageGenerator.generateImage(module,
                true, chop, imageFilePath,
                size, size, period, period, llkb, hlkb);
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
