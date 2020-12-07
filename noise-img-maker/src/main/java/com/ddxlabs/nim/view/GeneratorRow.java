package com.ddxlabs.nim.view;

import com.ddxlabs.nim.controller.Controllers;
import com.ddxlabs.nim.controller.ImageGenerationHandler;
import com.ddxlabs.nim.UserPreferences;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import java.util.Random;

/**
 *  One line panel at the bottom of the UI with:
 *     'Seed' field (populated randomly)
 *     'Randomize' button (builds an entire random structure - overrides current)
 *     'Generate' button - creates the art and
 *
 */
public class GeneratorRow implements ViewComponent, ActionListener {

    private TextTheme textTheme;

    private Random randomizer;

    private JTextField seedField;

    private ImageGenerationHandler imageGenHandler;

    public GeneratorRow(UserPreferences defaultPrefs) {
        this.textTheme = new TextTheme(defaultPrefs);
        this.randomizer = new SecureRandom();
    }

    @Override
    public void init(Controllers controllers) {
        this.imageGenHandler = controllers.getImageGenerationHandler();
    }

    @Override
    public JComponent buildUI() {
        JPanel row = new JPanel();
        //        seedField = new JTextField(16);
        //        long initialSeed = Math.abs(randomizer.nextLong());
        //        seedField.setText(String.valueOf(initialSeed));
        //        JButton seedGenerator = new JButton("Reset Seed");
        //        seedGenerator.setActionCommand("refresh_seed");
        //        seedGenerator.addActionListener(this);
        JButton preview = new JButton("Preview");
        preview.setActionCommand("preview");
        preview.addActionListener(this);

        JButton writeFile = new JButton("Save Image");
        writeFile.setActionCommand("write_file");
        writeFile.addActionListener(this);

        JButton saveAs = new JButton("Save Image As...");
        saveAs.setActionCommand("save_as_file");
        saveAs.addActionListener(this);

        // row.add(seedField);
        // row.add(seedGenerator);
        row.add(preview);
        row.add(writeFile);
        row.add(saveAs);

        return row;
    }

    @Override
    public void applyPreferences() {
    }

    public void actionPerformed(ActionEvent e) {
        if ("refresh_seed".equals(e.getActionCommand())) {
            long initialSeed = Math.abs(randomizer.nextLong());
            seedField.setText(String.valueOf(initialSeed));
        } else if ("preview".equals(e.getActionCommand())) {
            imageGenHandler.generateAndShowImage();
        } else if ("write_file".equals(e.getActionCommand())) {
            imageGenHandler.generateAndWriteFile();
        } else if ("save_as_file".equals(e.getActionCommand())) {
            imageGenHandler.generateAndSaveAsFile();
        }
    }

    public void setBaseSeed(int seed) {
        // seedField.setText(String.valueOf(seed));
    }
}
