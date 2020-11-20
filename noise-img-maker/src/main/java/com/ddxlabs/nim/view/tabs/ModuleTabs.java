package com.ddxlabs.nim.view.tabs;

import com.ddxlabs.nim.Controllers;
import com.ddxlabs.nim.UserPreferences;
import com.ddxlabs.nim.noise.NmType;
import com.ddxlabs.nim.view.TextTheme;
import com.ddxlabs.nim.view.ViewComponent;

import javax.swing.*;
import java.awt.*;
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

        Color tabColor = null;
        ModuleConfigTab configTab;
        switch (type) {
            case COMBO: configTab = new CombinerModuleConfigTab(moduleId); tabColor = Color.CYAN; break;
            case MODIFIER: configTab = new ModifierModuleConfigTab(moduleId); tabColor = Color.LIGHT_GRAY; break;
            default: configTab = new SourceModuleConfigTab(moduleId);
        }
        configTab.init(controllers);
        tabMap.put(moduleId, configTab);

        JComponent tabUI = configTab.buildUI();
        tabbedPane.addTab(titleForModule(moduleId), null, tabUI, moduleId);
        int tabIndex = tabbedPane.indexOfTab(titleForModule(moduleId));
        if (tabColor!=null) {
            tabbedPane.setBackgroundAt(tabIndex, tabColor);
        }
        tabbedPane.setSelectedIndex(tabIndex);

        showTabForModule(moduleId);
    }

    public ModuleConfigTab getConfigTabById(String moduleId) {
        return tabMap.get(moduleId);
    }

    @Override
    public void applyPreferences() {
    }

    public void refreshTabData() {
        tabMap.values().forEach(ModuleConfigTab::refreshTabData);
    }

    public ModuleConfigTab getTabByModuleId(String moduleId) {
        return tabMap.get(moduleId);
    }

    public void showTabForModule(String moduleId) {
        int tabIndex = tabbedPane.indexOfTab(titleForModule(moduleId));
        tabbedPane.setSelectedIndex(tabIndex);
    }

    private String titleForModule(String moduleId) {
        String title = moduleId;
        if (title.length()>8) {
            title = title.substring(0,7);
        }
        return title;
    }
}
