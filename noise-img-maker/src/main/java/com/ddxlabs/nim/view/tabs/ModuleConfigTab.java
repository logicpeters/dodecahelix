package com.ddxlabs.nim.view.tabs;

import com.ddxlabs.nim.controller.Controllers;
import com.ddxlabs.nim.controller.ImageGenerationHandler;
import com.ddxlabs.nim.controller.ModuleHandler;
import com.ddxlabs.nim.noise.NmType;
import com.ddxlabs.nim.view.ViewComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

/**
 *  Tabbed panel representing a module configuration.
 *
 *  Table with map of param key / value pairs
 *  Ability to modify any value
 *  Ability to add a new key/value pair, or delete an existing k/v pair
 *
 */
public abstract class ModuleConfigTab implements ViewComponent, ActionListener {

    protected ModuleHandler moduleHandler;

    protected ImageGenerationHandler imageGenerationHandler;

    protected String moduleId;

    protected ParamsMapTableView paramsTable;

    protected JPanel extraLabels;

    protected JPanel actionsRow;

    private ClickableModuleIdLabel parentLabel;

    private JLabel isRootLabel;

    public ModuleConfigTab(String moduleId) {
        this.moduleId = moduleId;
    }

    public void init(Controllers controllers) {
        this.moduleHandler = controllers.getModuleHandler();
        this.imageGenerationHandler = controllers.getImageGenerationHandler();
    }

    @Override
    public JComponent buildUI() {
        JPanel panel = new JPanel(false);
        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);

        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel moduleLabel = new JLabel("ID: " + moduleId);
        moduleLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        panel.add(moduleLabel);

        String qualifier = this.moduleHandler.getQualifierForModule(moduleId);
        NmType type = this.moduleHandler.getTypeForModule(moduleId);
        JLabel qualifierLabel = new JLabel(String.format("TYPE: %s (%s)", qualifier, type.name().toLowerCase()));
        qualifierLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        panel.add(qualifierLabel);

        boolean isRoot = this.moduleHandler.isModuleRoot(moduleId);
        isRootLabel = new JLabel("IS ROOT: " + isRoot);
        isRootLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        panel.add(isRootLabel);

        // whether ot not this module is attached to the structure?

        parentLabel = new ClickableModuleIdLabel("PARENT: %s", this.moduleHandler);
        Optional<String> parent = this.moduleHandler.getParent(moduleId);
        parent.ifPresent(s -> parentLabel.setModuleId(s));
        parentLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        panel.add(parentLabel);

        // this should be populated in the subclass
        extraLabels = new JPanel();
        extraLabels.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        extraLabels.setLayout(new BoxLayout(extraLabels, BoxLayout.Y_AXIS));
        panel.add(extraLabels);

        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        actionsRow = new JPanel();
        actionsRow.setLayout(new BoxLayout(actionsRow, BoxLayout.X_AXIS));
        actionsRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton validateButton = new JButton("Validate");
        validateButton.setActionCommand("validate");
        validateButton.addActionListener(this);
        actionsRow.add(validateButton);

        panel.add(actionsRow);

        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        paramsTable = new ParamsMapTableView();
        panel.add(new JScrollPane(paramsTable.buildTable(moduleId, moduleHandler, imageGenerationHandler)));

        return panel;
    }

    @Override
    public void applyPreferences() {
    }

    public String getModuleId() {
        return moduleId;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command!=null) {
            if ("make_root".equals(command)) {
                this.moduleHandler.setRootModule(moduleId);
            }
        }
    }

    public void refreshTabData() {
        // refresh the tab fields following a model change, such as adding a new module
        boolean isRoot = this.moduleHandler.isModuleRoot(moduleId);
        isRootLabel.setText("IS ROOT: " + isRoot);

        Optional<String> parent = this.moduleHandler.getParent(moduleId);
        if (parent.isPresent()) {
            parentLabel.setModuleId(parent.get());
        } else {
            parentLabel.setNoModule();
        }

        paramsTable.refreshParams();
    }

}
