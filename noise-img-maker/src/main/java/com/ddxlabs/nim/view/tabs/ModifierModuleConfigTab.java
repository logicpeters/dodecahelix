package com.ddxlabs.nim.view.tabs;

import javax.swing.*;
import java.awt.*;

public class ModifierModuleConfigTab extends ModuleConfigTab {

    public ModifierModuleConfigTab(String moduleId) {
        super(moduleId);
    }

    @Override
    public JComponent buildUI() {
        JComponent panel = super.buildUI();

        actionsRow.add(Box.createHorizontalGlue());

        return panel;
    }

    @Override
    public void refreshTabData() {
        super.refreshTabData();
    }

}
