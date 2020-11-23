package com.ddxlabs.nim.controller;

import com.ddxlabs.nim.*;
import com.ddxlabs.nim.noise.Models;
import com.ddxlabs.nim.noise.NmBuilder;
import com.ddxlabs.nim.noise.NoiseFileBuilder;
import com.ddxlabs.nim.utils.FileChooseUtils;
import com.ddxlabs.nim.view.Views;
import com.ddxlabs.nim.view.tabs.ModuleTabs;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class SystemHandler implements ControllerComponent {

    private Application app;
    private ModuleHandler moduleHandler;
    private ModuleTabs moduleTabs;

    public SystemHandler(UserPreferences prefs) {
    }

    @Override
    public void init(Controllers controllers, Views views, Models models) {
        this.moduleHandler = controllers.getModuleHandler();
        this.moduleTabs = views.getModuleTabs();
    }

    public void setApp(Application app) {
        this.app = app;
    }

    public JFrame getFrame() {
        return this.app.getFrame();
    }

    public void exitApp() {
        app.exitApp();
    }

    public int getCurrentAppHeight() {
        return app.getFrameHeight();
    }

    public void importFile() {
        Optional<File> chosenFile = FileChooseUtils.openFileChooserAndReturnFile(getFrame(),false, false,"NIM files", "nim");
        if (chosenFile.isPresent()) {
            System.out.println("Importing from " + chosenFile.get());
            try {
                NmBuilder builder = NoiseFileBuilder.buildModuleFromFile(chosenFile.get().getAbsolutePath());
                // clear all tabs
                this.moduleHandler.loadNewBuilderStructure(builder.getStructure(), builder.getParams());
                this.moduleTabs.reloadStructure();
            } catch (IOException e) {
                // TODO - present a dialog to user
                e.printStackTrace();
            }
        }
    }

    public void exportFile() {
        Optional<File> chosenFile = FileChooseUtils.openFileChooserAndReturnFile(getFrame(), true, false, null, (String[]) null);
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


}
