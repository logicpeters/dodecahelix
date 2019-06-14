package org.dmp.mongo.db;

import org.dmp.mongo.model.part.Part;

public interface PartDAO {

    public void add(Part part);
    
    public Part retrieveByPartNumber(String partNum);
    
    public void update(Part part);
    
    public void deleteByPartNumber(String partNum);
    
}
