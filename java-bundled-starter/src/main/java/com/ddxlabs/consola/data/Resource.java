package com.ddxlabs.consola.data;

import java.util.HashMap;
import java.util.Map;

public class Resource {

    private String id;

    /**
     *  Display value for this resource.
     */
    private String label;

    /**
     *  Using a Map name -> property for quick property lookup
     */
    private Map<String, Property> state;

    public Resource(String id) {
        this.id = id;
        this.label = id;
        this.state = new HashMap<>();
    }

    public Resource(String id, String label) {
        this(id);
        this.label = label;
    }

    public void addProperty(Property prop) {
        state.put(prop.getName(), prop);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Map<String, Property> getState() {
        return state;
    }

    public void setState(Map<String, Property> state) {
        this.state = state;
    }
}
