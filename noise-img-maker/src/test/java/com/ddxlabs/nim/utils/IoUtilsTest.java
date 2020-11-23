package com.ddxlabs.nim.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

class IoUtilsTest {

    @Test
    void testGetLocalFile() throws IOException {
        String localPath = "modules/sample_1234.csv";
        List<String> lines = FileUtils.stringListByLocalPath(localPath);
        Assertions.assertTrue(lines.size()>0);
    }

    @Test
    void testGetLocalIconImage() throws IOException {
        ImageIcon icon = IconUtils.getIcon("dot.png");
        Assertions.assertNotNull(icon.getImage());
    }

    @Test
    void testGetSpriteSheet() throws IOException {
        BufferedImage[][] sprites = IconUtils.getImagesFromSpriteSheet("runes.png",
                16, 15, 32, 32);
        for (int i=0; i<16; i++) {
            for (int j=0; j<15; j++) {
                BufferedImage bi = sprites[i][j];
                Assertions.assertNotNull(bi);
            }
        }
    }
}