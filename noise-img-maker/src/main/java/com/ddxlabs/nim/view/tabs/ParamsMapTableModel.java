package com.ddxlabs.nim.view.tabs;

import javax.swing.table.DefaultTableModel;

public class ParamsMapTableModel extends DefaultTableModel {

    public ParamsMapTableModel(Object[][] rowData, Object[] columnNames) {
        super(rowData, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == 1;
    }

}
