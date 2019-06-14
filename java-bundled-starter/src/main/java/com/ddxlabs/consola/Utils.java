package com.ddxlabs.consola;

import javax.swing.*;
import java.io.File;
import java.net.URL;

/**
 * Created on 5/30/2019.
 */
public class Utils {

    public static ImageIcon getIcon(String iconName) {
        String iconPath = String.format("/images/%s.png", iconName);
        System.out.println("Searching for image " + iconPath);
        URL iconURL = Utils.class.getResource(iconPath);
        return new ImageIcon(iconURL);
    }

}
