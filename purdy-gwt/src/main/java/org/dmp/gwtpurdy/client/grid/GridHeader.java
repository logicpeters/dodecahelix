package org.dmp.gwtpurdy.client.grid;

import java.util.HashMap;
import java.util.Map;

import org.dmp.gwtpurdy.client.base.BaseResource;
import org.dmp.gwtpurdy.client.grid.column.CheckboxColumn;
import org.dmp.gwtpurdy.client.grid.column.ColumnConfig;
import org.dmp.gwtpurdy.client.grid.data.GridRecord;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.ColumnSortEvent.Handler;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.SafeHtmlHeader;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ListDataProvider;

/**
 *   
 *   Simple grid with only a header.  Intended to act as fixed header for a content grid, 
 *   and pass events to the content grid
 *   
 * @author dmpuser
 *
 */
public class GridHeader extends Composite {

    public static final double CHKBOX_COLUMN_WIDTH = 40.0;

    private CellTable<GridRecord> grid;
    
    private FixedHeaderGrid gridPanel;
    
    private boolean checkboxColumn = false;
    private Map<Integer, Header<?>> headers = new HashMap<Integer, Header<?>>();
    
    // Create a data provider.
    private ListDataProvider<GridRecord> dataProvider = new ListDataProvider<GridRecord>();
    
    public GridHeader(FixedHeaderGrid parent, boolean hasCheckbox) {
        this.checkboxColumn = hasCheckbox;
        grid = new CellTable<GridRecord>(1, GridResource.INSTANCE);
        grid.setWidth("100%",true);
        
        HTML emptyTableWidget = new HTML("");
        emptyTableWidget.addStyleName(BaseResource.INSTANCE.baseCss().reset());
        grid.setEmptyTableWidget(emptyTableWidget);
        
        this.gridPanel = parent;
        
        initWidget(grid);
        
        // Connect the table to the data provider.
        dataProvider.addDataDisplay(grid);
        
        if (checkboxColumn) {
            addCheckboxColumn();
        }
    }
    
    private void addCheckboxColumn() {

        final CheckboxColumn column = new CheckboxColumn(GridRecord.SELECTOR_FIELD_NAME);

        // header cell should select all of the existing records
        CheckboxCell headerCell = new CheckboxCell() {
            boolean checked = false;
            
            @Override
            public void onBrowserEvent(Context context, Element parent, Boolean value, 
                NativeEvent event, ValueUpdater<Boolean> valueUpdater) {
                GWT.log("Native Event " + event.getType());
                  boolean isClick = "change".equals(event.getType());
                    checked = !checked;
                    if (isClick) {
                        GWT.log("Clicked checkbox header.  New state : " + checked);
                        gridPanel.checkAllColumns(checked);
                    }
            }
        };
        
        grid.insertColumn(0, column, new Header<Boolean>(headerCell) {
            @Override
            public Boolean getValue() {
                return false;
            }
        });

        grid.setColumnWidth(column, CHKBOX_COLUMN_WIDTH, Unit.PX);
        
        grid.addCellPreviewHandler(new CellPreviewEvent.Handler<GridRecord>() {
            @Override
            public void onCellPreview(CellPreviewEvent<GridRecord> event) {
                // is this the correct way of doing this?
                boolean isClick = "click".equals(event.getNativeEvent().getType());
                if (isClick && event.getColumn()==0) {
                    GridRecord record = event.getValue();
                    Boolean existing = column.getValue(record);
                    column.setValue(!existing, event.getValue());
                }
            }
        });
    }
    
    public void addColumn(final ColumnConfig columnConfig) {
        
        final Column<GridRecord,?> column = columnConfig.getColumn();
        final Header<SafeHtml> header = new SafeHtmlHeader(columnConfig.getHeader());

        grid.addColumn(column, header);
        final int i = grid.getColumnCount();
        headers.put(i, header);
        
        grid.addColumnSortHandler(new Handler() {
            @Override
            public void onColumnSort(ColumnSortEvent event) {
                // TODO Auto-generated method stub
                if (column.equals(event.getColumn())) {
                    gridPanel.sortColumn(i, event.isSortAscending());
                    grid.redrawHeaders();
                }
            }
        });
        
        if (columnConfig.getWidth()!=null) {
            grid.setColumnWidth(column, columnConfig.getWidth(), columnConfig.getWidthUnit());
        }
    }
    
}
