package org.dmp.gwtpurdy.client.grid.column;

import org.dmp.gwtpurdy.client.grid.data.GridField;
import org.dmp.gwtpurdy.client.grid.data.GridRecord;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.user.cellview.client.Column;

/**
 *   Standard checkbox column (using native checkbox, not Purdy Checkbox)
 *    
 * @author dmpuser
 *
 */
public class CheckboxColumn extends Column<GridRecord, Boolean> {
    
    private String fieldName;

    public CheckboxColumn(String fieldName) {
        super(new CheckboxCell());
        this.fieldName = fieldName;
    }

    @Override
    public Boolean getValue(GridRecord record) {
        @SuppressWarnings("unchecked")
        GridField<Boolean> field = (GridField<Boolean>) record.getField(fieldName);
        return field.getValue();
    }
    
    public void setValue(Boolean value, GridRecord record) {
        @SuppressWarnings("unchecked")
        GridField<Boolean> field = (GridField<Boolean>) record.getField(fieldName);
        field.setValue(value);
    }

}
