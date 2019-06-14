package org.dmp.gwtpurdy.client.grid.data;

public abstract class GridField<T> {
    
    private String name;
    private T value;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public T getValue() {
        return value;
    }
    public void setValue(T value) {
        this.value = value;
    }
    
    public abstract String getTextValue();

}
