package org.dmp.gwtpurdy.client.grid.column;

import org.dmp.gwtpurdy.client.grid.data.GridRecord;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;

/**
 *   Configuration for a generic column that will display a 
 * 
 * @author dmpuser
 *
 */
public class ColumnConfig {
	
	private String fieldName;
	
	private Double width;
	private Unit widthUnit;
	
	private SafeHtml header;
	
	private Column<GridRecord,?> column;
	
	private boolean sortable = true;
	
	public ColumnConfig(String fieldName) {
		this.fieldName = fieldName;
		
		// Use text column as a standard
       TextColumn<GridRecord> textColumn = new TextColumn<GridRecord>() {
            @Override
            public String getValue(GridRecord record) {
                return record.getCellTextValue(ColumnConfig.this.fieldName);
            }
        };
        this.column = textColumn;
	}
	
	public ColumnConfig(String fieldName, Column<GridRecord,?> column, SafeHtml header, double width, Unit widthUnit) {
		this(fieldName);
		// null column is a standard text column
		if (column!=null) {
		    this.column = column;
		}
		this.width = width;
		this.widthUnit = widthUnit;
		this.header = header;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setWidth(double width, Unit unit) {
		this.width = width;
		this.widthUnit = unit;
	}

	public Double getWidth() {
		return width;
	}

	public Unit getWidthUnit() {
		return widthUnit;
	}

	public SafeHtml getHeader() {
		return header;
	}

	public void setHeader(SafeHtml header) {
		this.header = header;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

    public Column<GridRecord, ?> getColumn() {
        return column;
    }

}
