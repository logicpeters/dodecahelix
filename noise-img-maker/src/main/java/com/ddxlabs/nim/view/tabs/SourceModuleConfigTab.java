package com.ddxlabs.nim.view.tabs;

import com.ddxlabs.nim.noise.ParamsMap;
import com.ddxlabs.nim.noise.modules.ModifierQualifier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public SourceModuleConfigTab(String moduleId) {
        super(moduleId);
    }

    @Override
    public JComponent buildUI() {
        JComponent panel = super.buildUI();

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

}
