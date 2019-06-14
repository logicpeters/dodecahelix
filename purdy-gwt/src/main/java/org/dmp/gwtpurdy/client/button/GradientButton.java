package org.dmp.gwtpurdy.client.button;

import org.dmp.gwtpurdy.client.base.BaseResource;
import org.dmp.gwtpurdy.client.color.ColorResource;
import org.dmp.gwtpurdy.client.color.ColorScheme;
import org.dmp.gwtpurdy.client.color.Colorizer;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Button;

public class GradientButton extends Button {
	
	private GradientButton() {
		this.setStyleName(BaseResource.INSTANCE.baseCss().gradientButton());
		this.addStyleName(BaseResource.INSTANCE.baseCss().gradientLightSprite());
		this.addStyleName(ColorResource.INSTANCE.colorCss().colorSchemeBase());
	}

	public GradientButton(String text) {
		this();
		this.setHTML(text);
	}
	
    public GradientButton(ImageResource img, String text) {
    	this();
     
		StringBuffer html = new StringBuffer("<img style='vertical-align:middle' src='");
		html.append(img.getSafeUri().asString());
		// TODO -- change to getUrl for backwards compatibility?
		
		html.append("'/><span style='display:inline-block; vertical-align:middle'>");
		html.append(text);
		html.append("</span>");
		this.setHTML(html.toString());
    }
	
    public void setColorScheme(ColorScheme scheme) {
        this.removeStyleName(BaseResource.INSTANCE.baseCss().gradientLightSprite());
        this.removeStyleName(ColorResource.INSTANCE.colorCss().colorSchemeBase());
        
        String colorStyle = Colorizer.getStyleForScheme(scheme);
        this.addStyleName(colorStyle);
    }
}
