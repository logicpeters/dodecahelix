package org.dmp.gwtpurdy.client.grid.column;

import java.util.Map;

import org.dmp.gwtpurdy.client.grid.data.GridRecord;

import com.google.gwt.cell.client.ImageResourceCell;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.Column;

/**
 *   Faster alternative to ImageColumn, where you have a static set of possible Images that you can render in any cell
 * 
 * @author dmpuser
 *
 */
public class ImageResourceColumn extends Column<GridRecord, ImageResource> {

    private String fieldName;
    private Map<String, ImageResource> resourceMap;
    
    /**
     * 
     * @param fieldName -- name of the GridRecord field that maps to the resource ID
     * @param resourceMap -- maps resource ID's to a ImageResource object
     */
    public ImageResourceColumn(String fieldName, Map<String,ImageResource> resourceMap) {
        super(new ImageResourceCell());
        
        this.fieldName = fieldName;
        this.resourceMap = resourceMap;
    }

    @Override
    public ImageResource getValue(GridRecord object) {
        String resourceId = object.getCellTextValue(fieldName);
        return resourceMap.get(resourceId);
    }

}
