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

    public SourceModuleConfigTab(String moduleId) {
        super(moduleId);
    }

    @Override
    public JComponent buildUI() {
        JComponent panel = super.buildUI();

        // gap
        actionsRow.add(Box.createRigidArea(new Dimension(15,0)));

        JButton makeRootButton = new JButton("Make Root");
        makeRootButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        makeRootButton.setActionCommand("make_root");
        makeRootButton.addActionListener(this);
        actionsRow.add(makeRootButton);

        actionsRow.add(Box.createHorizontalGlue());

        return panel;
    }

}
