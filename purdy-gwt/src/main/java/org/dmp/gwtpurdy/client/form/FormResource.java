package org.dmp.gwtpurdy.client.form;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;

public interface FormResource extends ClientBundle {

    public static final FormResource INSTANCE =  GWT.create(FormResource.class);

    @Source("form.css")
    public FormCss formCss();
    
    @ImageOptions(repeatStyle = RepeatStyle.Horizontal)
    @Source("gradient-field-30.png")
    ImageResource fieldGradient();
    
    @Source("checkbox-unchecked.png")
    ImageResource checkboxUnchecked();
    
    @Source("checkbox-checked.png")
    ImageResource checkboxChecked();
    
    @Source("radiobox-unchecked.png")
    ImageResource radioboxUnchecked();
    
    @Source("radiobox-checked.png")
    ImageResource radioboxChecked();
    
    @Source("resultset_next.png")
    ImageResource listItemNavigate();

}
