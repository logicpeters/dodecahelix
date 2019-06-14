package org.dmp.gwtpurdy.client.base;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;

public interface BaseResource extends ClientBundle {
    
    public static final BaseResource INSTANCE =  GWT.create(BaseResource.class);

    @Source("base.css")
    public BaseCss baseCss();
    
    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("bg-dark-square.png")
    ImageResource backgroundDarkSquares();
    
    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("bg-dark-jag.png")
    ImageResource backgroundDarkJag();
    
    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("bg-dark-straw.png")
    ImageResource backgroundDarkStraw();
    
    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("bg-light-square.png")
    ImageResource backgroundLightSquares();
    
    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("bg-light-flat.png")
    ImageResource backgroundLightFlat();
    
    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("bg-light-straw.png")
    ImageResource backgroundLightStraw();
    
    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("bg-grey-jag.png")
    ImageResource backgroundGreyJag();
    
    @ImageOptions(repeatStyle = RepeatStyle.Both)
    @Source("bg-grey-flat.png")
    ImageResource backgroundGreyFlat();
    
    @ImageOptions(repeatStyle = RepeatStyle.Horizontal)
    @Source("gradient-button-40.png")
    ImageResource buttonGradient();
    
    @ImageOptions(repeatStyle = RepeatStyle.Horizontal)
    @Source("gradient-button-reversed-40.png")
    ImageResource buttonGradientReversed();
    
    @ImageOptions(repeatStyle = RepeatStyle.Horizontal)
    @Source("gradient-field.png")
    ImageResource lightGradient();
    
    @ImageOptions(repeatStyle = RepeatStyle.Horizontal)
    @Source("gradient-aqua.png")
    ImageResource blueGradient();
    
    @ImageOptions(repeatStyle = RepeatStyle.Horizontal)
    @Source("gradient-red.png")
    ImageResource redGradient();
    
    @ImageOptions(repeatStyle = RepeatStyle.Horizontal)
    @Source("hr-light.png")
    ImageResource hrLine();

    @Source("ajax-loader.gif")
	public ImageResource waitSpinner();
    
}
