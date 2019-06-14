package org.dmp.gwtpurdy.client.grid.column;

import org.dmp.gwtpurdy.client.grid.data.GridRecord;

import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.user.cellview.client.Column;

/**
 * 
 *  A column where the image that appears is rendered via a URL relative to a certain path (and using a certain extension)
 *  
 *   
 * @author dmpuser
 *
 */
public class ImageColumn extends Column<GridRecord, String> {

    private String fieldName;
    private String imagePath;
    private String imageExtension;
    
    public ImageColumn(String field, String imagePath, String imageExtension) {
        super(new ImageCell());
        this.fieldName = field;
        this.imagePath = imagePath;
        this.imageExtension = imageExtension;
    }

    @Override
    public String getValue(GridRecord record) {
        StringBuffer imageUrl = new StringBuffer(imagePath);
        
        String imageResourceId = record.getCellTextValue(fieldName);
        if (imageResourceId != null && imageResourceId.trim().length()>0) {
            imageUrl.append(imageResourceId);
        } else {
            imageUrl.append("");
        }
        
        if (imageExtension!=null) {
            imageUrl.append(".");
            imageUrl.append(imageExtension);
        }
        
        return imageUrl.toString();
    }

}
