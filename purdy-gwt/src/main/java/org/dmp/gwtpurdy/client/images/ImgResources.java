package org.dmp.gwtpurdy.client.images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ImgResources extends ClientBundle {

    public ImgResources INSTANCE = GWT.create(ImgResources.class);
    
    @Source("cancel.png")
    ImageResource cancelIcon();

    @Source("information.png")
    ImageResource information();

    @Source("exclamation.png")
    ImageResource error();

    @Source("lock.png")
    ImageResource security();
    
    @Source("bullet_arrow_down.png")
    ImageResource downArrowBullet();
    
    @Source("bullet_arrow_up.png")
    ImageResource upArrowBullet();
    
    @Source("arrow_down.png")
    ImageResource downArrow();
    
    @Source("arrow_up.png")
    ImageResource upArrow();

}
