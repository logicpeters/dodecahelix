package com.ddxlabs.nim.view;

import com.ddxlabs.nim.Controllers;
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

    public GeneratorRow(UserPreferences defaultPrefs, Controllers controllers) {
        this.textTheme = new TextTheme(defaultPrefs);
        randomizer = new SecureRandom();
        this.imageGenHandler = controllers.getImageGenerationHandler();
    }

    @Override
    public JComponent buildUI() {
        JPanel row = new JPanel();
        seedField = new JTextField(16);
        long initialSeed = Math.abs(randomizer.nextLong());
        seedField.setText(String.valueOf(initialSeed));
        JButton seedGenerator = new JButton("Reset Seed");
        seedGenerator.setActionCommand("refresh_seed");
        seedGenerator.addActionListener(this);
        JButton generateArt = new JButton("Generate Art");
        generateArt.setActionCommand("generate_art");
        generateArt.addActionListener(this);
        row.add(seedField);
        row.add(seedGenerator);
        row.add(generateArt);
        return row;
    }

    @Override
    public void applyPreferences(UserPreferences preferences) {
    }

    public void actionPerformed(ActionEvent e) {
        if ("refresh_seed".equals(e.getActionCommand())) {
            long initialSeed = Math.abs(randomizer.nextLong());
            seedField.setText(String.valueOf(initialSeed));
        } else if ("generate_art".equals(e.getActionCommand())) {
            imageGenHandler.generateAndShowImage();
        }
    }

    public void setBaseSeed(int seed) {
        seedField.setText(String.valueOf(seed));
    }
}
