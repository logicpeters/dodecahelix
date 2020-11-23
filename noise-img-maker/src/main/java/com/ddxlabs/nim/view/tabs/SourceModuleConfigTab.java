package com.ddxlabs.nim.view.tabs;

import com.ddxlabs.nim.noise.modules.ModifierQualifier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
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

    private ClickableModuleIdLabel modifierLabel;

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
        modifierLabel = new ClickableModuleIdLabel("MODIFIER: %s", moduleHandler);
        Optional<String> modifierOpt = this.moduleHandler.getModifierFor(moduleId);
        modifierOpt.ifPresent(s -> modifierLabel.setModuleId(s));
        modifierLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        extraLabels.add(modifierLabel);

        // gap
        actionsRow.add(Box.createRigidArea(new Dimension(15,0)));

        JButton makeRootButton = new JButton("Make Root");
        makeRootButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        makeRootButton.setActionCommand("make_root");
        makeRootButton.addActionListener(this);
        actionsRow.add(makeRootButton);

        // gap
        actionsRow.add(Box.createRigidArea(new Dimension(15,0)));

        // add modifier
        String[] modifierOptions = Arrays.stream(ModifierQualifier.values()).sequential()
                .map(m -> m.name().toLowerCase())
                .toArray(String[]::new);
        modifierList = new JComboBox<>(modifierOptions);
        modifierList.setMaximumSize(new Dimension(50,30));
        // modifierList.setPreferredSize(new Dimension(30,0));
        actionsRow.add(modifierList);

        JButton modifyButton = new JButton("Add Modifier");
        modifyButton.setActionCommand("add_modifier");
        modifyButton.addActionListener(this);
        actionsRow.add(modifyButton);

        actionsRow.add(Box.createHorizontalGlue());

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
        if (modifierModuleId.isPresent()) {
            modifierLabel.setModuleId(modifierModuleId.get());
        } else {
            modifierLabel.setNoModule();
        }
    }

}
