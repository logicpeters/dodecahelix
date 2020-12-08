package com.ddxlabs.nim.controller;

import com.ddxlabs.nim.noise.Models;
import com.ddxlabs.nim.UserPreferences;
import com.ddxlabs.nim.utils.FileChooseUtils;
import com.ddxlabs.nim.view.Views;
import com.ddxlabs.nim.noise.ImageGenerator;

import com.ddxlabs.nim.noise.NmBuilder;
import com.ddxlabs.nim.view.ImagePreviewView;
import org.spongepowered.noise.module.Module;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class ImageGenerationHandler implements ControllerComponent {

    private final UserPreferences prefs;
    private NmBuilder moduleBuilder;
    private ImagePreviewView imagePreviewView;
    private SystemHandler systemHandler;

    public ImageGenerationHandler(UserPreferences prefs) {
        this.prefs = prefs;
    }

    public void init(Controllers controllers, Views views, Models models) {
        this.moduleBuilder = models.getNmBuilder();
        this.imagePreviewView = views.getImageView();
        this.systemHandler = controllers.getSystemHandler();
    }

    public void generateAndSaveAsFile() {
        JFrame frame = systemHandler.getFrame();
        String imageFolderPath = prefs.getPreference(UserPreferences.KEY_IMAGE_FOLDER);
        File imageFolder = new File(imageFolderPath);
        Optional<File> file = FileChooseUtils.openFileChooserAndReturnFile(frame, imageFolder, true, false, null);
        if (file.isPresent()) {
            String filePath = file.get().getAbsolutePath();
            if (!filePath.contains(".")) {
                filePath += ".png";
            }
            generateAndWriteFile(filePath);
        }
    }

    public void generateAndWriteFile() {
        String imageFolder = prefs.getPreference(UserPreferences.KEY_IMAGE_FOLDER) + System.getProperty("file.separator");
        String imageFilePath = imageFolder + moduleBuilder.getStructure().getRootModuleId() + ".png";
        generateAndWriteFile(imageFilePath);
    }

    private void generateAndWriteFile(String imageFilePath) {
        BufferedImage image = generateImage();
        ImageGenerator.writeImageToFile(imageFilePath, image);
    }

    public void generateAndShowImage() {
        try {
            BufferedImage image = generateImage();
            imagePreviewView.previewImage(image);
            addImageToPreviewPanel(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean generatoImageWithinLimits(int lowerLimit, int upperLimit) {
        try {
            BufferedImage image = generateImage();
            int imageSize = ImageGenerator.getSizeOfImage(image);
            System.out.println("image size is " + imageSize);
            if (imageSize>lowerLimit && imageSize<upperLimit) {
                imagePreviewView.previewImage(image);
                addImageToPreviewPanel(image);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }

        return false;
    }

    private BufferedImage generateImage() {
        Module module = moduleBuilder.build();

        boolean color = Boolean.parseBoolean(prefs.getPreference(UserPreferences.KEY_IMAGE_COLOR));
        int chop = prefs.getIntPreference(UserPreferences.KEY_IMAGE_CHOP);
        int size = prefs.getIntPreference(UserPreferences.KEY_IMAGE_PIXEL_SIZE);
        int period = prefs.getIntPreference(UserPreferences.KEY_IMAGE_PERIOD);

        // recalculate size for the preview, we will use this only for writing the file
        int appHeight = systemHandler.getCurrentAppHeight();
        // size = (appHeight / period) * period;  // NOTE: the first part will be rounded down, so imgHeight < appHeight

        return ImageGenerator.generateImage(module, color, chop,
                size, size, period, period);
    }

    private void addImageToPreviewPanel(BufferedImage image) throws IOException {
        ImageIcon icon = new ImageIcon(image);
        imagePreviewView.showImage(icon);
    }

}
