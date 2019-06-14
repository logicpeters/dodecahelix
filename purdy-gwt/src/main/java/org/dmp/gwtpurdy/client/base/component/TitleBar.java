package org.dmp.gwtpurdy.client.base.component;

import org.dmp.gwtpurdy.client.base.BaseResource;
import org.dmp.gwtpurdy.client.color.ColorResource;
import org.dmp.gwtpurdy.client.color.ColorScheme;
import org.dmp.gwtpurdy.client.color.Colorizable;
import org.dmp.gwtpurdy.client.color.Colorizer;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class TitleBar extends Composite implements Colorizable {
    
	private HorizontalPanel frame;
    private HTML titleLabel = new HTML("");
    
    private TitleBar() {
    	frame = new HorizontalPanel();
        frame.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        
        initWidget(frame);

        this.setStyleName(BaseResource.INSTANCE.baseCss().windowTitleBar());
        //this.addStyleName(FormResource.INSTANCE.formCss().formFieldGradient());
        this.addStyleName(ColorResource.INSTANCE.colorCss().colorSchemeLightBlue());
    }

    public TitleBar(String title) {
        this();
        titleLabel.setHTML(title);
        frame.add(titleLabel);
    }
    
    public TitleBar(ImageResource icon, String title) {
        this();
        titleLabel = new IconLabel(icon, title);
        frame.add(titleLabel);
    }
    
    public void setTitleLabel(String text) {
        titleLabel.setText(text);
    }

    @Override
    public void setColor(ColorScheme color) {
        Colorizer.colorize(this, color);        
    }
    
}
