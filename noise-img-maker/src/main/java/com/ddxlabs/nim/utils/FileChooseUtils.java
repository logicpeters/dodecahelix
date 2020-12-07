package com.ddxlabs.nim.utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.Optional;

public class FileChooseUtils {

    public static Optional<File> openFileChooserAndReturnFile(JFrame frame,
                                                        File defaultPath,
                                                        boolean save,
                                                        boolean folder,
                                                        String description,
                                                        String... extensions) {
        // open up a file chooser
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(defaultPath);
        int returnVal;
        if (save) {
            returnVal = chooser.showSaveDialog(frame);
        } else if (folder) {
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            returnVal = chooser.showOpenDialog(frame);
        } else {
            FileNameExtensionFilter filter = new FileNameExtensionFilter(description, extensions);
            chooser.setFileFilter(filter);
            returnVal = chooser.showOpenDialog(frame);
        }

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return Optional.of(chooser.getSelectedFile());
        } else {
            return Optional.empty();
        }
    }



}
