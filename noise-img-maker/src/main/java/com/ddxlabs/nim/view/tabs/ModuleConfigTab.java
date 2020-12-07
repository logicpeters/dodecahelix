package com.ddxlabs.nim.view.tabs;

import com.ddxlabs.nim.controller.Controllers;
import com.ddxlabs.nim.controller.ImageGenerationHandler;
import com.ddxlabs.nim.controller.ModuleHandler;
import com.ddxlabs.nim.noise.NmType;
import com.ddxlabs.nim.noise.modules.ModifierQualifier;
import com.ddxlabs.nim.view.ViewComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
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

    private JComboBox<String> modifierList;

    private ClickableModuleIdLabel modifierLabel;

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

        // whether or not this module has a modifier
        Optional<String> modifierModuleId = this.moduleHandler.getModifierFor(moduleId);
        String modifier = "None";
        if (modifierModuleId.isPresent()) {
            modifier = modifierModuleId.get();
        }
        modifierLabel = new ClickableModuleIdLabel("MODIFIER: %s", moduleHandler);
        Optional<String> modifierOpt = this.moduleHandler.getModifierFor(moduleId);
        modifierOpt.ifPresent(s -> modifierLabel.setModuleId(s));
        modifierLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        panel.add(modifierLabel);

        // this should be populated in the subclass
        extraLabels = new JPanel();
        extraLabels.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        extraLabels.setLayout(new BoxLayout(extraLabels, BoxLayout.Y_AXIS));
        panel.add(extraLabels);

        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        actionsRow = new JPanel();
        actionsRow.setLayout(new BoxLayout(actionsRow, BoxLayout.X_AXIS));
        actionsRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setActionCommand("delete_tab");
        deleteButton.addActionListener(this);
        actionsRow.add(deleteButton);

        // gap
        actionsRow.add(Box.createRigidArea(new Dimension(15,0)));

        // add modifier
        String[] modifierOptions = Arrays.stream(ModifierQualifier.values()).sequential()
                .map(m -> m.name().toLowerCase())
                .toArray(String[]::new);
        modifierList = new JComboBox<>(modifierOptions);
        modifierList.setMaximumSize(new Dimension(50,30));
        actionsRow.add(modifierList);

        JButton modifyButton = new JButton("Add Modifier");
        modifyButton.setActionCommand("add_modifier");
        modifyButton.addActionListener(this);
        actionsRow.add(modifyButton);

        actionsRow.add(Box.createRigidArea(new Dimension(15,0)));

        JButton randomizeButton = new JButton("Randomize");
        randomizeButton.setActionCommand("randomize");
        randomizeButton.addActionListener(this);
        actionsRow.add(randomizeButton);

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
            if ("delete_tab".equals(command)) {
                this.moduleHandler.deleteModule(moduleId);
            }
            if ("add_modifier".equals(command)) {
                this.moduleHandler.addModifierModule(moduleId, (String)modifierList.getSelectedItem());
            }
            if ("randomize".equals(command)) {
                this.moduleHandler.randomizeParams(moduleId);
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

        Optional<String> modifierModuleId = this.moduleHandler.getModifierFor(moduleId);
        if (modifierModuleId.isPresent()) {
            modifierLabel.setModuleId(modifierModuleId.get());
        } else {
            modifierLabel.setNoModule();
        }

        paramsTable.refreshParams();
    }

}
