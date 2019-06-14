package org.dmp.gwtpurdy.client.grid.data;

public class GridStringField extends GridField<String> {

    public GridStringField(String name, String value) {
        this.setName(name);
        this.setValue(value);
    }
    
    @Override
    public String getTextValue() {
        return this.getValue();
    }

}
