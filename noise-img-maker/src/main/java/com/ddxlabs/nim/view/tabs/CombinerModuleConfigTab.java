package com.ddxlabs.nim.view.tabs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class CombinerModuleConfigTab extends ModuleConfigTab {

    private List<String> sourceModules;
    private JPanel sourceModulesPanel;
    private JComboBox<String> sourceModuleChooser;

    public CombinerModuleConfigTab(String moduleId) {
        super(moduleId);
        sourceModules = new ArrayList<>();
        sourceModuleChooser = new JComboBox<>();
    }

    @Override
    public JComponent buildUI() {
        JComponent panel = super.buildUI();

        extraLabels.add(Box.createRigidArea(new Dimension(0,15)));
        JLabel sourceModuleHeader = new JLabel("SOURCE MODULES:");
        sourceModuleHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        extraLabels.add(sourceModuleHeader);
        extraLabels.add(Box.createRigidArea(new Dimension(0,5)));

        sourceModulesPanel = new JPanel();
        sourceModulesPanel.setLayout(new BoxLayout(sourceModulesPanel, BoxLayout.Y_AXIS));
        populateSourceModules();
        extraLabels.add(sourceModulesPanel);

        extraLabels.add(Box.createRigidArea(new Dimension(0,10)));

        // choices to add to source modules
        List<String> choices = this.moduleHandler.getUnattachedModules();
        choices.remove(moduleId);
        JPanel sourceModuleChooser = buildSourceModuleChooser(choices);
        sourceModuleChooser.setAlignmentX(Component.LEFT_ALIGNMENT);
        extraLabels.add(sourceModuleChooser);

        // add actions button
        actionsRow.add(Box.createRigidArea(new Dimension(15, 0)));

        JButton makeRootButton = new JButton("Make Root");
        makeRootButton.setActionCommand("make_root");
        makeRootButton.addActionListener(this);
        actionsRow.add(makeRootButton);
        return panel;
    }

    private void populateSourceModules() {
        sourceModules = this.moduleHandler.getChildModules(moduleId);
        for (String childModule: sourceModules) {
            ClickableModuleIdLabel label = new ClickableModuleIdLabel("Source: %s", moduleHandler);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            label.setModuleId(childModule);
            sourceModulesPanel.add(label);
        }
    }

    private JPanel buildSourceModuleChooser(List<String> choices) {
        JPanel choosePanel = new JPanel();
        choosePanel.setLayout(new BoxLayout(choosePanel, BoxLayout.X_AXIS));

        sourceModuleChooser = new JComboBox<String>(choices.toArray(new String[0]));
        sourceModuleChooser.addActionListener(this);
        choosePanel.add(sourceModuleChooser);

        choosePanel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton addSourceButton = new JButton("Add as Source");
        addSourceButton.setActionCommand("add_source_module");
        addSourceButton.addActionListener(this);
        choosePanel.add(addSourceButton);

        choosePanel.add(Box.createHorizontalGlue());
        return choosePanel;
    }

    @Override
    public void refreshTabData() {
        super.refreshTabData();

        // refresh module choices
        List<String> choices = this.moduleHandler.getUnattachedModules();
        choices.remove(moduleId);
        DefaultComboBoxModel<String> chooserModel = (DefaultComboBoxModel<String>) sourceModuleChooser.getModel();
        chooserModel.removeAllElements();
        chooserModel.addAll(choices);

        // refresh combobox choices
        sourceModulesPanel.removeAll();
        populateSourceModules();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);

        if ("add_source_module".equalsIgnoreCase(e.getActionCommand())) {
            String newChild = (String) sourceModuleChooser.getSelectedItem();
            List<String> currentChildren = this.moduleHandler.getChildModules(moduleId);
            currentChildren.add(newChild);

            ClickableModuleIdLabel label = new ClickableModuleIdLabel("Source: %s", moduleHandler);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            label.setModuleId(newChild);
            sourceModulesPanel.add(label);
            sourceModulesPanel.revalidate();
            this.moduleHandler.setSourceModulesForCombo(moduleId, currentChildren);
        }
    }
}
