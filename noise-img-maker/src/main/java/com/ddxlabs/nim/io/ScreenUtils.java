package com.ddxlabs.nim.io;

import java.awt.*;

public class ScreenUtils {

    /**
     * Based on the current screen size, recommend a square height/width in pixels.
     *
     * The application will be slightly larger than (width = 2*size, height = size).
     *
     * NOTE: if the user has resized the application, the image will be squashed.
     *
     * @param period : the result size should be a multiple of this number
     * @param scaleFactor : double between 0 and 1 representing the percentage of screen width to make the app (i.e; 0.8)
     * @return
     */
    public static int recommendSquareSize(int period, double scaleFactor) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        double recommendedSquareSize = screenHeight * scaleFactor;
        if (screenHeight*2 > screenWidth) {
            // narrow screen - use width as the
            recommendedSquareSize = (screenWidth / 2) * scaleFactor;
        }

        // make this number a multiple of period
        recommendedSquareSize = Math.floor(recommendedSquareSize / period) * period;
        return (int)recommendedSquareSize;
    }

}
