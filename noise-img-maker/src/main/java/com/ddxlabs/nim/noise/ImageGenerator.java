package com.ddxlabs.nim.noise;

import org.spongepowered.noise.module.Module;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferUShort;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageGenerator {

    /**
     *  Generate image from Noise module.
     *
     * @param module
     * @param color
     * @param chop
     * @param width
     * @param height
     * @param xPeriod
     * @param yPeriod
     * @return - true or false: whether or not the file was written (may be outside file size limit)
     */
    public static BufferedImage generateImage(Module module, boolean color, int chop,
                                        int width, int height, double xPeriod, double yPeriod) {

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_USHORT_GRAY);
        if (color) {
            image = new BufferedImage(width, height, BufferedImage.TYPE_USHORT_555_RGB);
        }

        final short[] data = ((DataBufferUShort) image.getRaster().getDataBuffer()).getData();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double noise = module.getValue(x / xPeriod, y / yPeriod, 0) / 2;
                if (chop>0) {
                    // chop is a way of producing discreet values for noise
                    noise = Math.ceil(noise * chop) / chop;
                }
                data[y * width + x] = (short) (noise * 65_535);
            }
        }

        // System.out.println("bytes size is " + getSizeOfImage(image));
        return image;
    }

    public static boolean writeImageToFile(String filename, BufferedImage image) {
        try {
            File file = new File(filename);
            ImageIO.write(image, "PNG", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gets the image size without writing to disk.
     *
     * @param img
     * @return
     */
    public static int getSizeOfImage(BufferedImage img) {
        byte[] imageBytes = new byte[0];
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);
            imageBytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageBytes.length;
    }
}
