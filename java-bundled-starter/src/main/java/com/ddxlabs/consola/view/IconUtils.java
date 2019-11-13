package com.ddxlabs.consola.view;

import com.ddxlabs.consola.ConsolaException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created on 5/30/2019.
 */
public class IconUtils {

    public static ImageIcon getIcon(String iconName) {
        String iconPath = String.format("images/%s.png", iconName);
        URL iconURL = IconUtils.class.getResource(iconPath);
        return new ImageIcon(iconURL);
    }

    public static BufferedImage[][] getImagesFromSpritesheet(String spriteSheetName,
                                                             int cols, int rows,
                                                             int spriteWidth, int spriteHeight) {

        BufferedImage[][] sprites = new BufferedImage[cols][rows];
        try {
            File spriteFile = new File(IconUtils.class.getResource(String.format("sprites/%s.png", spriteSheetName)).getFile());
            BufferedImage bigImg = ImageIO.read(spriteFile);
            // The above line throws an checked IOException which must be caught.

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
        } catch (IOException ioe) {
            throw new ConsolaException(ioe);
        }

        return sprites;
    }

}
