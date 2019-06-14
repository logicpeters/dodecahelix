package org.dmp.gwtpurdy.client.grid;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.dmp.gwtpurdy.client.grid.column.CheckboxColumn;
import org.dmp.gwtpurdy.client.grid.column.ColumnConfig;
import org.dmp.gwtpurdy.client.grid.data.GridRecord;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

/**
 *   A simple grid without fixed headers and optional checkbox column
 * 
 * @author dmpuser
 *
 */
public class GridView extends Composite {

	private CellTable<GridRecord> grid;
	
	private List<ColumnConfig> columnConfigs = new ArrayList<ColumnConfig>();
	
	// Create a data provider.
	private ListDataProvider<GridRecord> dataProvider = new ListDataProvider<GridRecord>();
	
	public GridView() {
		this(Integer.MAX_VALUE);
	}
	
	public GridView(int pageSize) {
		//this.setStyleName(BaseResou)
		grid = new CellTable<GridRecord>(pageSize, GridResource.INSTANCE);
		grid.setWidth("100%");
		
		initWidget(grid);
		
		// Connect the table to the data provider.
		dataProvider.addDataDisplay(grid);
	}	
	
	public void addCheckboxColumn() {
		final CheckboxColumn column = new CheckboxColumn(GridRecord.SELECTOR_FIELD_NAME);

		grid.insertColumn(0, column);
		grid.setColumnWidth(column, 20.0, Unit.PX);
		
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
		columnConfigs.add(columnConfig);
		
		Column<GridRecord,?> column = columnConfig.getColumn();
		
		if (columnConfig.getHeader()==null) {
			grid.addColumn(column);
		} else {
			grid.addColumn(column, columnConfig.getHeader());
		}
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
}
