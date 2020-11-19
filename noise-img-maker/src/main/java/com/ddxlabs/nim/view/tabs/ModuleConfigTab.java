package com.ddxlabs.nim.view.tabs;

import com.ddxlabs.nim.UserPreferences;
import com.ddxlabs.nim.noise.ParamsMap;
import com.ddxlabs.nim.view.ViewComponent;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.util.Map;

/**
 *  Tabbed panel representing a module configuration.
 *
 *  Table with map of param key / value pairs
 *  Ability to modify any value
 *  Ability to add a new key/value pair, or delete an existing k/v pair
 *
 */
public abstract class ModuleConfigTab implements ViewComponent, TableModelListener {

    private String moduleId;
    private String qualifier;
    private ParamsMap params;
    private ParamsMapTableModel tableModel;

    public ModuleConfigTab(String moduleId, String qualifier, ParamsMap params) {
        this.moduleId = moduleId;
        this.qualifier = qualifier;
        this.params = params;
    }

    @Override
    public JComponent buildUI() {
        JPanel panel = new JPanel(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel moduleLabel = new JLabel(moduleId);
        panel.add(moduleLabel);

        JLabel qualifierLabel = new JLabel(qualifier);
        panel.add(qualifierLabel);

        panel.add(buildParamsMapTable());

        return panel;
    }

    public JComponent buildParamsMapTable() {
        //headers for the table
        String[] columns = new String[] {
                "Parameter", "Value"
        };

        //actual data for the table in a 2d array
        Map<String, String> paramsForModule = params.getEntriesForModule(moduleId);
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
        tableModel.addTableModelListener(this);

        //add the table to the frame
        return new JScrollPane(table);
    }

    @Override
    public void applyPreferences(UserPreferences preferences) {

    }

    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        if (column==1) {
            String paramName = tableModel.getValueAt(row, 0).toString();
            String paramValue = tableModel.getValueAt(row, 1).toString();
            String previousValue = params.getModuleValue(moduleId, paramName);

            // TODO - make sure new value is the same format

            System.out.printf("changing value of %s from %s to %s%n", paramName, previousValue, paramValue);
            params.resetValue(moduleId, paramName, paramValue);
        }
    }

    public String getModuleId() {
        return moduleId;
    }

    public String getQualifier() {
        return qualifier;
    }

    public ParamsMap getParams() {
        return params;
    }
}
