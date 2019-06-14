package org.dmp.mongo.model.part;

import javax.persistence.Id;

public class Part {

    @Id
    private String id;
    
    private String number;
    private String title;
    private String description;
    
    public Part() {
    }
    
    public Part(String partNumber) {
        this.number = partNumber;
    }

    public String getNumber() {
        return number;
    }
    
    public void setNumber(String number) {
        this.number = number;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
