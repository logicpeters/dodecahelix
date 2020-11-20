package com.ddxlabs.nim.view.tabs;

import com.ddxlabs.nim.Controllers;
import com.ddxlabs.nim.controller.ModuleHandler;
import com.ddxlabs.nim.view.ViewComponent;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Optional;

/**
 *  Tabbed panel representing a module configuration.
 *
 *  Table with map of param key / value pairs
 *  Ability to modify any value
 *  Ability to add a new key/value pair, or delete an existing k/v pair
 *
 */
public abstract class ModuleConfigTab implements ViewComponent, TableModelListener, ActionListener {

    protected ModuleHandler moduleHandler;

    protected String moduleId;

    private ParamsMapTableModel tableModel;

    protected JPanel extraLabels;

    protected JPanel actionsRow;

    private ClickableModuleIdLabel parentLabel;

    private JLabel isRootLabel;

    public ModuleConfigTab(String moduleId) {
        this.moduleId = moduleId;
    }

    public void init(Controllers controllers) {
        this.moduleHandler = controllers.getModuleHandler();
    }

    @Override
    public JComponent buildUI() {
        JPanel panel = new JPanel(false);
        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);

        JLabel moduleLabel = new JLabel("ID: " + moduleId);
        moduleLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        panel.add(moduleLabel);

        String qualifier = this.moduleHandler.getQualifierForModule(moduleId);
        JLabel qualifierLabel = new JLabel("TYPE: " + qualifier);
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
        extraLabels.setLayout(new BoxLayout(extraLabels, BoxLayout.Y_AXIS));
        extraLabels.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        panel.add(extraLabels);

        actionsRow = new JPanel();
        actionsRow.setLayout(new BoxLayout(actionsRow, BoxLayout.X_AXIS));
        actionsRow.setAlignmentX( Component.LEFT_ALIGNMENT );//0.0
        JButton validateButton = new JButton("Validate");
        validateButton.setActionCommand("validate");
        validateButton.addActionListener(this);
        actionsRow.add(validateButton);
        actionsRow.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        panel.add(actionsRow);

        panel.add(buildParamsMapTable());

        return panel;
    }

    public JComponent buildParamsMapTable() {
        //headers for the table
        String[] columns = new String[] {
                "Parameter", "Value"
        };

        //actual data for the table in a 2d array
        Map<String, String> paramsForModule = moduleHandler.getParamsForModule(moduleId);

        Object[][] data = new Object[paramsForModule.size()][2];
        int keyNum = 0;
        for (String key: paramsForModule.keySet()) {
            data[keyNum][0] = key;
            data[keyNum][1] = paramsForModule.get(key);
            keyNum++;
        }

        //create table with data
        tableModel = new ParamsMapTableModel(data, columns);
        JTable table = new JTable(tableModel);
        table.setRowHeight(30);

        DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {
            Border padding = BorderFactory.createEmptyBorder(0, 10, 0, 0);
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(BorderFactory.createCompoundBorder(getBorder(), padding));
                return this;
            }
        };
        table.setDefaultRenderer(Object.class, tableCellRenderer);
        tableModel.addTableModelListener(this);

        //add the table to the frame
        return new JScrollPane(table);
    }

    @Override
    public void applyPreferences() {
    }

    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        if (column==1) {
            String paramName = tableModel.getValueAt(row, 0).toString();
            String paramValue = tableModel.getValueAt(row, 1).toString();
            String previousValue = moduleHandler.getParamValueForModule(moduleId, paramName);

            // TODO - make sure new value is the same format

            System.out.printf("changing value of %s from %s to %s%n", paramName, previousValue, paramValue);
            moduleHandler.setParamValueForModule(moduleId, paramName, paramValue);
        }
    }

    public String getModuleId() {
        return moduleId;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("make_root".equals(e.getActionCommand())) {
            this.moduleHandler.setRootModule(moduleId);
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

        // TODO - refresh table
    }

}
