package org.dmp.gwtpurdy.client.color;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface ColorResource extends ClientBundle {

    public static final ColorResource INSTANCE =  GWT.create(ColorResource.class);

    @Source("color.css")
    public ColorCss colorCss();
    
}
