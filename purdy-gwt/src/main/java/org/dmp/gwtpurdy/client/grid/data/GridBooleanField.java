package org.dmp.gwtpurdy.client.grid.data;

public class GridBooleanField extends GridField<Boolean> {

	public GridBooleanField(String fieldName, Boolean bool) {
        this.setName(fieldName);
        
        if (bool==null) {
        	bool = false;
        }
        this.setValue(bool);
	}

	@Override
	public String getTextValue() {
		return this.getValue().toString();
	}

}
