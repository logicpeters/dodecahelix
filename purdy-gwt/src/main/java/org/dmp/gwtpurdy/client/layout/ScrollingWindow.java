package org.dmp.gwtpurdy.client.layout;

import org.dmp.gwtpurdy.client.base.BaseResource;
import org.dmp.gwtpurdy.client.base.component.TitleBar;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 *   Basic framed panel with a titlebar and a interior scrollpanel (fixed height)
 * 
 * @author dmpuser
 *
 */
public class ScrollingWindow extends Composite {
    
    private FlowPanel dockView;
    private ScrollPanel scrollView;
    
    public ScrollingWindow(String title, Integer panelWidth, Integer panelHeight) {
        dockView = new FlowPanel();
        scrollView = new ScrollPanel();
        scrollView.setHeight("150px");
       
        if (title!=null) {
            dockView.add(new TitleBar(title));
        }
        dockView.add(scrollView);
    
        this.initWidget(dockView);
        
        this.setWidth(panelWidth.toString());
        this.setHeight(panelHeight.toString());
        
        this.addStyleName(BaseResource.INSTANCE.baseCss().window());
        this.addStyleName(BaseResource.INSTANCE.baseCss().unroundedBorder());
    }
    
    public void setScrollingContent(Widget content) {
        scrollView.setWidget(content);
    }
}
