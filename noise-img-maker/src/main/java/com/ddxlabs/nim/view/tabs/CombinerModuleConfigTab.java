package com.ddxlabs.nim.view.tabs;

import javax.swing.*;
import java.util.List;

public class CombinerModuleConfigTab extends ModuleConfigTab {

    // up to 4 source modules
    public JComboBox[] sourceModuleChoosers = new JComboBox[4];

    public CombinerModuleConfigTab(String moduleId) {
        super(moduleId);
    }

    @Override
    public JComponent buildUI() {
        JComponent panel = super.buildUI();

        JButton makeRootButton = new JButton("Make Root");
        makeRootButton.setActionCommand("make_root");
        makeRootButton.addActionListener(this);
        actionsRow.add(makeRootButton);

        extraLabels.add(new JLabel("SOURCE MODULES:"));
        List<String> chosen = this.moduleHandler.getChildModules(moduleId);
        List<String> choices = this.moduleHandler.getChildModules(moduleId);
        choices.addAll(this.moduleHandler.getUnattachedModules());
        choices.remove(moduleId);
        for (int i=0; i<4; i++) {
            JComboBox<String> sourceModuleChooser = new JComboBox<String>(choices.toArray(new String[0]));
            if (i < chosen.size()) {
                sourceModuleChooser.setSelectedItem(chosen.get(i));
            }
            sourceModuleChooser.addActionListener(this);
            sourceModuleChoosers[i] = sourceModuleChooser;
            extraLabels.add(sourceModuleChooser);
        }

        return panel;
    }

    @Override
    public void refreshTabData() {
        super.refreshTabData();

        List<String> chosen = this.moduleHandler.getChildModules(moduleId);
        List<String> choices = this.moduleHandler.getChildModules(moduleId);
        choices.addAll(this.moduleHandler.getUnattachedModules());
        choices.remove(moduleId);
        for (int i=0; i<4; i++) {
            JComboBox<String> sourceModuleChooser = sourceModuleChoosers[i];
            DefaultComboBoxModel comboBoxModel = (DefaultComboBoxModel) sourceModuleChooser.getModel();
            comboBoxModel.removeAllElements();
            comboBoxModel.addAll(choices);
            if (i < chosen.size()) {
                sourceModuleChooser.setSelectedItem(chosen.get(i));
            }
        }
    }
}
