package org.dmp.gwtpurdy.showcase;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ShowcaseResources extends ClientBundle {
    
    public static final ShowcaseResources INSTANCE =  GWT.create(ShowcaseResources.class);

    @Source("showcase.css")
    public ShowcaseCss showcaseCss();
    
    @Source("user.png")
    ImageResource user();

    @Source("user_zorro.png")
    ImageResource userZorro();

    @Source("user_red.png")
    ImageResource userRed();
    
    @Source("user_female.png")
    ImageResource userFemale();

    @Source("user_policeman.png")
    ImageResource userPolice();
    
    @Source("bullet_orange.png")
    ImageResource listItemIcon();

    @Source("application_form.png")
    ImageResource formIcon();

    @Source("comment.png")
	ImageResource dialogIcon();

	@Source("table.png")
	ImageResource gridIcon();

	@Source("shape_align_left.png")
	ImageResource statusIcon();

	@Source("application_view_list.png")
	ImageResource listIcon();
}
