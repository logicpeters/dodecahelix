package com.ddxlabs.nim.controller;

import com.ddxlabs.nim.Preset;
import com.ddxlabs.nim.UserPreferences;
import com.ddxlabs.nim.noise.Models;
import com.ddxlabs.nim.noise.NmBuilder;
import com.ddxlabs.nim.noise.NoiseFileBuilder;
import com.ddxlabs.nim.utils.FileChooseUtils;
import com.ddxlabs.nim.view.Views;
import com.ddxlabs.nim.view.tabs.ModuleTabs;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

public class ImportExportHandler implements ControllerComponent {

    private UserPreferences prefs;

    private SystemHandler systemHandler;
    private ImageGenerationHandler imgeGenHandler;
    private ModuleHandler moduleHandler;
    private ModuleTabs moduleTabs;

    public ImportExportHandler(UserPreferences prefs) {
        this.prefs = prefs;
    }

    @Override
    public void init(Controllers controllers, Views views, Models models) {
        this.systemHandler = controllers.getSystemHandler();
        this.moduleHandler = controllers.getModuleHandler();
        this.moduleTabs = views.getModuleTabs();
        this.imgeGenHandler = controllers.getImageGenerationHandler();
    }

    public void importFile() {
        File defaultPath = new File(prefs.getPreference(UserPreferences.KEY_IMAGE_FILE_FOLDER));
        Optional<File> chosenFile = FileChooseUtils.openFileChooserAndReturnFile(systemHandler.getFrame(),
                defaultPath, false, false,"NIM files", "nim");
        if (chosenFile.isPresent()) {
            System.out.println("Importing from " + chosenFile.get());
            try {
                NoiseFileBuilder fileBuilder = new NoiseFileBuilder();
                NmBuilder builder = fileBuilder.buildModuleFromFile(chosenFile.get().getAbsolutePath());
                // clear all tabs
                this.moduleHandler.loadNewBuilderStructure(builder.getStructure(), builder.getParams());
                this.moduleTabs.reloadStructure();
                this.imgeGenHandler.generateAndShowImage();
            } catch (IOException e) {
                // TODO - present a dialog to user
                e.printStackTrace();
            }
        }
    }

    public void exportFile() {
        File defaultPath = new File(prefs.getPreference(UserPreferences.KEY_IMAGE_FILE_FOLDER));
        Optional<File> chosenFile = FileChooseUtils.openFileChooserAndReturnFile(systemHandler.getFrame(),
                defaultPath, true, false, null, (String[]) null);
        if (chosenFile.isPresent()) {
            File exportFile = chosenFile.get();
            String exportFilePath = exportFile.getAbsolutePath();
            if (!exportFilePath.endsWith(".nim")) {
                exportFilePath = String.format("%s.nim", exportFilePath);
            }

            System.out.println("Exporting to " + chosenFile.get().getAbsolutePath());
            try {
                NoiseFileBuilder.writeNoiseTreeToFile(this.moduleHandler.getStructureAsCsv(),
                        this.moduleHandler.getParamsAsCsv(),
                        exportFilePath);
            } catch (IOException e) {
                // TODO - show error to user
                e.printStackTrace();
            }
        }
    }

    public void importPreset(Preset preset) {
        try {
            String presetPath = preset.getPath();
            URL resource = this.getClass().getResource(presetPath);
            File file = new File(resource.toURI());
            NoiseFileBuilder fileBuilder = new NoiseFileBuilder();
            NmBuilder builder = fileBuilder.buildModuleFromFile(file.getAbsolutePath());
            // clear all tabs
            this.moduleHandler.loadNewBuilderStructure(builder.getStructure(), builder.getParams());
            this.moduleTabs.reloadStructure();
            this.imgeGenHandler.generateAndShowImage();
        } catch (IOException | URISyntaxException e) {
            // TODO - present a dialog to user
            e.printStackTrace();
        }
    }
}
