package org.dmp.gwtpurdy.client.base.component;

import org.dmp.gwtpurdy.client.base.BaseResource;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HTML;

public class IconLabel extends HTML {
    
    private String imageUrl;
    private SafeHtml text;
    
    private IconLabel() {
        this.addStyleName(BaseResource.INSTANCE.baseCss().iconLabel());
    }
    
    public IconLabel(ImageResource img, String text) {
        this();
        imageUrl = img.getSafeUri().asString();
        this.text = SafeHtmlUtils.fromString(text);
        this.setHTML(generateHtml());
    }

    public IconLabel(ImageResource img, SafeHtml htmlText) {
        this();
        imageUrl = img.getSafeUri().asString();
        this.text = htmlText;
        this.setHTML(generateHtml());
    }
    
    private String generateHtml() {
        StringBuffer html = new StringBuffer("<img style='vertical-align:middle' src='");
        html.append(imageUrl);
        html.append("'/><span style='display:inline-block; vertical-align:middle'>");
        html.append(text.asString());
        html.append("</span>");
        return html.toString();
    }
    
    public void setText(String text) {
        this.text = SafeHtmlUtils.fromString(text);
        this.setHTML(generateHtml());
    }
    
}
