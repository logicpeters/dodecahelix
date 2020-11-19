package com.ddxlabs.nim.view.tabs;

import com.ddxlabs.nim.Controllers;
import com.ddxlabs.nim.UserPreferences;
import com.ddxlabs.nim.io.IconUtils;
import com.ddxlabs.nim.noise.NmBuilder;
import com.ddxlabs.nim.noise.NmType;
import com.ddxlabs.nim.noise.ParamsMap;
import com.ddxlabs.nim.noise.StructureMap;
import com.ddxlabs.nim.view.TextTheme;
import com.ddxlabs.nim.view.ViewComponent;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ModuleTabs implements ViewComponent {

    private TextTheme textTheme;

    private Controllers controllers;

    private Map<String, ModuleConfigTab> tabMap;

    private JTabbedPane tabbedPane;

    public ModuleTabs(UserPreferences preferences) {
        this.textTheme = new TextTheme(preferences);
        this.tabMap = new HashMap<>();
    }

    @Override
    public void init(Controllers controllers) {
        this.controllers = controllers;
    }

    @Override
    public JComponent buildUI() {
        tabbedPane = new JTabbedPane();
        return tabbedPane;
    }

    public void addModule(NmType type,
                          String moduleId) {

        ModuleConfigTab configTab;
        switch (type) {
            case COMBO: configTab = new CombinerModuleConfigTab(moduleId); break;
            case MODIFIER: configTab = new ModifierModuleConfigTab(moduleId); break;
            default: configTab = new SourceModuleConfigTab(moduleId);
        }
        configTab.init(controllers);
        tabMap.put(moduleId, configTab);
        String title = moduleId.substring(0, moduleId.indexOf("-"));
        tabbedPane.addTab(title, null, configTab.buildUI(), moduleId);
    }

    public ModuleConfigTab getConfigTabById(String moduleId) {
        return tabMap.get(moduleId);
    }

    @Override
    public void applyPreferences() {
    }
}
