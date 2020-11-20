package com.ddxlabs.nim.view.tabs;

import com.ddxlabs.nim.noise.modules.ModifierQualifier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Optional;

/**
 *  Configures a source module
 *
 *  TODO:
 *  Add field for "is root? yes/no" w/ button for "Make Root"
 *  Add field for "combo of" w/ button for "Combine With"
 *  Add field for "modified by" w/ button for "Modify With"
 */
public class SourceModuleConfigTab extends ModuleConfigTab {

    private JComboBox<String> modifierList;

    private JLabel modifierLabel;

    public SourceModuleConfigTab(String moduleId) {
        super(moduleId);
    }

    @Override
    public JComponent buildUI() {
        JComponent panel = super.buildUI();

        Optional<String> modifierModuleId = this.moduleHandler.getModifierFor(moduleId);
        String modifier = "None";
        if (modifierModuleId.isPresent()) {
            modifier = modifierModuleId.get();
        }
        // TODO - update this if modifier added
        modifierLabel = new JLabel("MODIFIER: " + modifier);
        modifierLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        extraLabels.add(modifierLabel);

        JButton makeRootButton = new JButton("Make Root");
        makeRootButton.setActionCommand("make_root");
        makeRootButton.addActionListener(this);
        actionsRow.add(makeRootButton);

        // add modifier
        JPanel addModPanel = new JPanel();
        String[] modifierOptions = Arrays.stream(ModifierQualifier.values()).sequential()
                .map(m -> m.name().toLowerCase())
                .toArray(String[]::new);
        modifierList = new JComboBox<>(modifierOptions);
        addModPanel.add(modifierList);

        JButton modifyButton = new JButton("Add Modifier");
        modifyButton.setActionCommand("add_modifier");
        modifyButton.addActionListener(this);
        addModPanel.add(modifyButton);

        actionsRow.add(addModPanel);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        if ("add_modifier".equals(e.getActionCommand())) {
            this.moduleHandler.addModifierModule(moduleId, (String)modifierList.getSelectedItem());
        }
    }

    @Override
    public void refreshTabData() {
        super.refreshTabData();

        Optional<String> modifierModuleId = this.moduleHandler.getModifierFor(moduleId);
        String modifier = "None";
        if (modifierModuleId.isPresent()) {
            modifier = modifierModuleId.get();
        }
        modifierLabel.setText("MODIFIER: " + modifier);
    }
}
