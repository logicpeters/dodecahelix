package com.ddxlabs.nim.noise;

import org.spongepowered.noise.module.Module;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferUShort;
import java.io.File;
import java.io.IOException;

public class ImageGenerator {

    public static boolean generateImage(Module module, boolean color, int chop, String filename,
                                        int width, int height, double xPeriod, double yPeriod,
                                        int lowLimitKb, int highLimitKb) {

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_USHORT_GRAY);
        if (color) {
            image = new BufferedImage(width, height, BufferedImage.TYPE_USHORT_555_RGB);
        }

        final short[] data = ((DataBufferUShort) image.getRaster().getDataBuffer()).getData();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double noise = module.getValue(x / xPeriod, y / yPeriod, 0) / 2;
                noise = Math.ceil(noise * chop) / chop;
                data[y * width + x] = (short) (noise * 65_535);
            }
        }

        try {
            File file = new File(filename);
            if (file.exists()) {
                // delete the old file
                System.out.println("overwriting previous image");
            }
            ImageIO.write(image, "PNG", file);
            double kb = file.length() / 1000.0;
            System.out.println("file length is " + kb + " kb");
            if (kb>lowLimitKb && kb<highLimitKb) {
                return true;
            } else {
                file.delete();
                System.out.println("file is outside size limits");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
