package org.dmp.gwtpurdy.client.grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.dmp.gwtpurdy.client.grid.column.CheckboxColumn;
import org.dmp.gwtpurdy.client.grid.column.ColumnConfig;
import org.dmp.gwtpurdy.client.grid.data.GridRecord;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.ColumnSortList.ColumnSortInfo;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

/**
 *   Grid that only manages content.  Separate from the header so that the header can be in a fixed state
 * 
 * @author dmpuser
 *
 */
public class GridContent extends Composite {

	private CellTable<GridRecord> grid;
	
	private List<ColumnConfig> columnConfigs = new ArrayList<ColumnConfig>();
	
	// Create a data provider.
	private ListDataProvider<GridRecord> dataProvider = new ListDataProvider<GridRecord>();
	
	private CheckboxColumn checkboxColumn;
	
	private boolean paged = false;
	
	public GridContent(boolean hasCheckboxColumn) {
		this(Integer.MAX_VALUE, hasCheckboxColumn);
	}
	
	public GridContent(int pageSize, boolean hasCheckboxColumn) {
		grid = new CellTable<GridRecord>(pageSize, GridResource.INSTANCE);
		grid.setWidth("100%", true);
		paged = true;
		
		initWidget(grid);
		
		// Connect the table to the data provider.
		dataProvider.addDataDisplay(grid);
		
		if (hasCheckboxColumn) {
		    this.addCheckboxColumn();
		}
	}	
	
	protected void addCheckboxColumn() {
	    checkboxColumn = new CheckboxColumn(GridRecord.SELECTOR_FIELD_NAME);

		grid.insertColumn(0, checkboxColumn);
		grid.setColumnWidth(checkboxColumn, GridHeader.CHKBOX_COLUMN_WIDTH, Unit.PX);
		
		grid.addCellPreviewHandler(new CellPreviewEvent.Handler<GridRecord>() {
			@Override
			public void onCellPreview(CellPreviewEvent<GridRecord> event) {
				// is this the correct way of doing this?
				boolean isClick = "click".equals(event.getNativeEvent().getType());
				if (isClick && event.getColumn()==0) {
					GridRecord record = event.getValue();
					Boolean existing = checkboxColumn.getValue(record);
					checkboxColumn.setValue(!existing, event.getValue());
				}
			}
		});
	}	

    public void checkAllColumns(boolean checked) {
        GWT.log("Checking all columns with " + checked);
        for (GridRecord record :  dataProvider.getList()) {
            checkboxColumn.setValue(checked, record);
            GWT.log(record.toString());
        }
        grid.redraw();
    }
	
	public void addColumn(final ColumnConfig columnConfig) {
		columnConfigs.add(columnConfig);
		
		Column<GridRecord,?> column = columnConfig.getColumn();
		grid.addColumn(column);
		
		if (columnConfig.getWidth()!=null) {
			grid.setColumnWidth(column, columnConfig.getWidth(), columnConfig.getWidthUnit());
		}
		
		if (columnConfig.isSortable()) {
			column.setSortable(true);
			
			// simple alphabetical sorting
			ListHandler<GridRecord> columnSortHandler = new ListHandler<GridRecord>(dataProvider.getList());
			columnSortHandler.setComparator(column,	new Comparator<GridRecord>() {
				public int compare(GridRecord recordOne, GridRecord recordTwo) {
					if (recordOne != null) {
						return (recordTwo != null) ? recordOne.getCellTextValue(columnConfig.getFieldName()).compareTo(recordTwo.getCellTextValue(columnConfig.getFieldName())) : 1;
					}
					return -1;
				}
			});
			
			grid.addColumnSortHandler(columnSortHandler);
		}
		
	}
	
	public void addRecord(GridRecord record) {
		List<GridRecord> list = dataProvider.getList();
		list.add(record);
	}
	
	public void addRecords(List<GridRecord> records) {
		List<GridRecord> list = dataProvider.getList();
		for (GridRecord record : records) {
			list.add(record);
		}
	}
	
	public void setSingleSelect() {		
		grid.setSelectionModel(new SingleSelectionModel<GridRecord>());
	}
	
	public void setMultiSelect() {
		grid.setSelectionModel(new MultiSelectionModel<GridRecord>());
	}

	public boolean hasCheckboxColumn() {
	    boolean hasCheckboxColumn = false;
	    if (checkboxColumn!=null) {
	        hasCheckboxColumn = true;
	    }
	    return hasCheckboxColumn;
	}
	
    protected boolean isPaged() {
        return paged;
    }

    protected void addPagerControl(SimplePager pager) {
        pager.setDisplay(grid);
    }

    public void sortColumn(int columnIdx, final boolean sortAscending) {
        // TODO -- this could get out of sync
        ColumnConfig cc = columnConfigs.get(columnIdx-2);
        final String fieldName = cc.getFieldName();
        GWT.log("GridContent::sortColumn -- Sorting column " + fieldName + " (ascending = " + sortAscending + ")");
        if (!cc.isSortable()) {
            GWT.log("GridContent::sortColumn -- Column is not sortable."); return;
        }
        
        // necessary?
        ColumnSortInfo columnSortInfo = new ColumnSortInfo(cc.getColumn(), sortAscending);
        grid.getColumnSortList().push(columnSortInfo);
        grid.redraw();
        
        List<GridRecord> dataList = dataProvider.getList();
        Collections.sort(dataList, new Comparator<GridRecord>() {
            @Override
            public int compare(GridRecord recordOne, GridRecord recordTwo) {
                int comparison = -1;
                if (recordOne != null) {
                    comparison = (recordTwo != null) ? recordOne.getCellTextValue(fieldName).compareTo(recordTwo.getCellTextValue(fieldName)) : 1;
                }
                if (!sortAscending) {
                    comparison = -comparison;
                }
                
                return comparison;
            }
        });
        
        // necessary?
        dataProvider.setList(dataList);
        
    }

}
