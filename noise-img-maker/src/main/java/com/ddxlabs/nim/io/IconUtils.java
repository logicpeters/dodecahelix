package com.ddxlabs.nim.io;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.InputStream;

/**
 *  Reading images and icons from local resource paths
 */
public class IconUtils {

    public static ImageIcon getIcon(String iconName) throws IOException {
        String iconPath = String.format("/images/%s", iconName);
        // InputStream iconImageStream = IconUtils.class.getClassLoader().getResourceAsStream(iconPath);
        InputStream iconImageStream = IconUtils.class.getResourceAsStream(iconPath);
        if (iconImageStream==null) {
            throw new IOException("could not read icon file from path " + iconPath);
        }
        byte[] imageBytes = iconImageStream.readAllBytes();
        return new ImageIcon(imageBytes);
    }

    public static BufferedImage[][] getImagesFromSpriteSheet(String spriteSheetName,
                                                             int cols, int rows,
                                                             int spriteWidth, int spriteHeight) throws IOException {

        BufferedImage[][] sprites = new BufferedImage[cols][rows];
        String spriteSheetPath = String.format("sprites/%s", spriteSheetName);
        InputStream spriteSheetImageStream = IconUtils.class.getClassLoader().getResourceAsStream(spriteSheetPath);
        if (spriteSheetImageStream==null) {
            throw new IOException("could not read sprite-sheet file from path " + spriteSheetPath);
        }

        BufferedImage bigImg = ImageIO.read(spriteSheetImageStream);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sprites[j][i] = bigImg.getSubimage(
                        j * spriteWidth,
                        i * spriteHeight,
                        spriteWidth,
                        spriteHeight
                );
            }
        }

        return sprites;
    }

}
