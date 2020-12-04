package com.ddxlabs.nim.view.tabs;

import com.ddxlabs.nim.controller.ImageGenerationHandler;
import com.ddxlabs.nim.controller.ModuleHandler;
import com.ddxlabs.nim.utils.NumberUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ParamsMapTableView implements TableModelListener, MouseListener {

    private JTable table;
    private ParamsMapTableModel tableModel;

    private String moduleId;
    private ModuleHandler moduleHandler;
    private ImageGenerationHandler imageGenerationHandler;

    public JTable buildTable(String moduleId, ModuleHandler moduleHandler, ImageGenerationHandler imageGenerationHandler) {
        this.moduleId = moduleId;
        this.moduleHandler = moduleHandler;
        this.imageGenerationHandler = imageGenerationHandler;

        tableModel = generateTableModel();
        table = new JTable(tableModel);

        table.setRowHeight(30);

        // adds padding to the cells (necessary??)
        DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {
            Border padding = BorderFactory.createEmptyBorder(0, 10, 0, 0);
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(BorderFactory.createCompoundBorder(getBorder(), padding));
                if (column == 2) {
                    return new JButton("+");
                }
                if (column == 3) {
                    return new JButton("-");
                }
                return this;
            }
        };

        table.setDefaultRenderer(Object.class, tableCellRenderer);
        tableModel.addTableModelListener(this);
        table.addMouseListener(this);
        return table;
    }

    public ParamsMapTableModel generateTableModel() {
        //headers for the table
        String[] columns = new String[] {
                "Parameter", "Value", "Inc", "Dec"
        };

        //actual data for the table in a 2d array
        Map<String, String> paramsForModule = this.moduleHandler.getParamsForModule(moduleId);
        Object[][] data = new Object[paramsForModule.size()][4];
        AtomicInteger index = new AtomicInteger(0);
        paramsForModule.keySet().stream().sorted().forEach(key -> {
            int row = index.getAndIncrement();
            data[row][0] = key;
            data[row][1] = paramsForModule.get(key);
            data[row][2] = key;
            data[row][3] = key;
        });

        return new ParamsMapTableModel(data, columns);
    }

    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        if (column==1) {
            String paramName = tableModel.getValueAt(row, 0).toString();
            String paramValue = tableModel.getValueAt(row, 1).toString();
            String previousValue = moduleHandler.getParamValueForModule(moduleId, paramName);

            // TODO - make sure new value is the same format
            boolean matchesFormat = NumberUtils.matchesFormat(paramValue, previousValue);
            if (matchesFormat && !previousValue.equalsIgnoreCase(paramValue)) {
                moduleHandler.setParamValueForModule(moduleId, paramName, paramValue);
                refreshParams();
            }
        }
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent event) {
        int row = table.rowAtPoint(event.getPoint());
        int col = table.columnAtPoint(event.getPoint());
        String paramKey = tableModel.getValueAt(row, 0).toString();
        if (col == 2) {
            moduleHandler.incrementParam(moduleId, paramKey, false, 1);
        }
        if (col == 3) {
            moduleHandler.incrementParam(moduleId, paramKey, true, 1);
        }

        imageGenerationHandler.generateAndShowImage();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void refreshParams() {
        Map<String, String> params = this.moduleHandler.getParamsForModule(moduleId);
        int numRows = tableModel.getRowCount();
        for (int row=0; row<numRows; row++) {
            String paramName = String.valueOf(tableModel.getValueAt(row, 0));
            String previousValue = String.valueOf(tableModel.getValueAt(row, 1));
            String newValue = params.get(paramName);
            if (!newValue.equalsIgnoreCase(previousValue)) {
                tableModel.setValueAt(params.get(paramName), row, 1);
            }
        }
    }
}
