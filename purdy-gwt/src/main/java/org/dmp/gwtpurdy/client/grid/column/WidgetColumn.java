package org.dmp.gwtpurdy.client.grid.column;

import org.dmp.gwtpurdy.client.grid.data.GridRecord;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Widget;

public class WidgetColumn extends Column<GridRecord, Widget> {

    public WidgetColumn(Cell<Widget> cell) {
        super(cell);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Widget getValue(GridRecord record) {
        // TODO Auto-generated method stub
        return null;
    }

}
