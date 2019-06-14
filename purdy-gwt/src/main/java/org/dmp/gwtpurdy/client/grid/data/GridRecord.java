package org.dmp.gwtpurdy.client.grid.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GridRecord {
    
	private Map<String, GridField<?>> fields = new HashMap<String, GridField<?>>();
	
	/**
	 *   Selector checkbox field (optional)
	 */
	public static final String SELECTOR_FIELD_NAME = "selectorCheckboxField";
	
	public GridRecord() {
		GridField<Boolean> bf = new GridBooleanField(SELECTOR_FIELD_NAME, new Boolean(false));
		fields.put(SELECTOR_FIELD_NAME, bf);
	}
	
	/**
	 *   Constructor for a simple string-based grid record
	 * 
	 * @param records
	 */
	public GridRecord(String[][] records) {
		this();
		for (String[] record : records) {
			GridField<String> gf = new GridStringField(record[0], record[1]);
			fields.put(record[0], gf);
		}
	}
	
	public GridField<?> getField(String fieldName) {
	    return fields.get(fieldName);
	}

	public String getCellTextValue(String fieldName) {
	    GridField<?> field = fields.get(fieldName);
		return field.getTextValue();
	}	
	
	public Collection<GridField<?>> getFields() {
	    return fields.values();
	}
	
	public String toString() {
	    StringBuffer string = new StringBuffer("Record:");
	    for (GridField<?> field : fields.values()) {
	        string.append(field.getName());
	        string.append("=");
	        string.append(field.getTextValue());
	        string.append(";");
	    }
	    return string.toString();
	}
}
