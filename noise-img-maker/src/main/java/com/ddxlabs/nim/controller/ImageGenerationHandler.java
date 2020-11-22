package com.ddxlabs.nim.controller;

import com.ddxlabs.nim.Controllers;
import com.ddxlabs.nim.Models;
import com.ddxlabs.nim.UserPreferences;
import com.ddxlabs.nim.Views;
import com.ddxlabs.nim.noise.ImageGenerator;

import com.ddxlabs.nim.noise.NmBuilder;
import com.ddxlabs.nim.view.ImagePreviewView;
import org.spongepowered.noise.module.Module;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageGenerationHandler implements ControllerComponent {

    private final UserPreferences prefs;
    private NmBuilder moduleBuilder;
    private ImagePreviewView imagePreviewView;
    private SystemHandler systemHandler;

    private JFrame reusableFrame;
    private JLabel imageLabel;

    public ImageGenerationHandler(UserPreferences prefs) {
        this.prefs = prefs;
    }

    public void init(Controllers controllers, Views views, Models models) {
        this.moduleBuilder = models.getNmBuilder();
        this.imagePreviewView = views.getImageView();
        this.systemHandler = controllers.getSystemHandler();
    }

    public void generateAndWriteFile() {
        Module module = moduleBuilder.build();
        String imageFolder = prefs.getPreference(UserPreferences.KEY_IMAGE_FOLDER) + System.getProperty("file.separator");
        String imageFilePath = imageFolder + moduleBuilder.getStructure().getRootModuleId() + ".png";

        boolean color = Boolean.parseBoolean(prefs.getPreference(UserPreferences.KEY_IMAGE_COLOR));
        int chop = prefs.getIntPreference(UserPreferences.KEY_IMAGE_CHOP);
        int size = prefs.getIntPreference(UserPreferences.KEY_IMAGE_PIXEL_SIZE);
        int period = prefs.getIntPreference(UserPreferences.KEY_IMAGE_PERIOD);
        int llkb = prefs.getIntPreference(UserPreferences.KEY_IMAGE_LOW_LIMIT_KB);
        int hlkb = prefs.getIntPreference(UserPreferences.KEY_IMAGE_HIGH_LIMIT_KB);

        BufferedImage image = ImageGenerator.generateImage(module, color, chop,
                size, size, period, period);

        if (ImageGenerator.writeImageToFile(imageFilePath, llkb, hlkb, image)) {
            // success
        } else {
            // image was either too large or too small and was not written
            // TODO - popup warning
        }
    }

    public void generateAndShowImage() {
        Module module = moduleBuilder.build();

        boolean color = Boolean.parseBoolean(prefs.getPreference(UserPreferences.KEY_IMAGE_COLOR));
        int chop = prefs.getIntPreference(UserPreferences.KEY_IMAGE_CHOP);
        int size = prefs.getIntPreference(UserPreferences.KEY_IMAGE_PIXEL_SIZE);
        int period = prefs.getIntPreference(UserPreferences.KEY_IMAGE_PERIOD);

        // recalculate size for the preview, we will use this only for writing the file
        int appHeight = systemHandler.getCurrentAppHeight();
        // size = (appHeight / period) * period;  // NOTE: the first part will be rounded down, so imgHeight < appHeight
        System.out.println("size is " + size);

        BufferedImage image = ImageGenerator.generateImage(module, color, chop,
                size, size, period, period);

        try {
            imagePreviewView.previewImage(image);
            addImageToPreviewPanel(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addImageToPreviewPanel(BufferedImage image) throws IOException {
        ImageIcon icon = new ImageIcon(image);
        imagePreviewView.showImage(icon);
    }

}
