package org.dmp.gwtpurdy.client.base.component;

import org.dmp.gwtpurdy.client.base.BaseResource;
import org.dmp.gwtpurdy.client.color.ColorScheme;
import org.dmp.gwtpurdy.client.color.Colorizer;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class StatusBar extends Composite {

	private HorizontalPanel frame;
	
	public StatusBar() {
		frame = new HorizontalPanel();
        frame.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        
        initWidget(frame);
        
        this.setStyleName(BaseResource.INSTANCE.baseCss().statusBar());
        this.addStyleName(BaseResource.INSTANCE.baseCss().unroundedBorder());
	}
	
	public void setWidth(int width, Unit unit) {
		String widthString = (new Integer(width)).toString() + unit.getType();
		frame.setWidth(widthString);
	}
	
	public void setStatus(int current, int max, String statusMessage, ColorScheme color) {
		if (current>max) {
			current = max;
		}
		int percent = (current * 100) / max;
		int blankPercent = 100 - percent;
		
		boolean overFifty = false;
		if (percent>50) {
			overFifty = true;
		}
		
		HTML colorBar = new HTML("&nbsp;");
		colorBar.addStyleName(Colorizer.getStyleForScheme(color));
		frame.add(colorBar);
		frame.setCellWidth(colorBar, percent + Unit.PCT.getType());
		
		HTML blankBar = new HTML("&nbsp;");
		blankBar.addStyleName(Colorizer.getStyleForScheme(ColorScheme.BASE));
		frame.add(blankBar);
		frame.setCellWidth(blankBar, blankPercent + Unit.PCT.getType());
		
		if (overFifty) {
			colorBar.setHTML("<span style='padding-right: 5px;'>" + statusMessage + "</span>");
			frame.setCellHorizontalAlignment(colorBar, HasHorizontalAlignment.ALIGN_RIGHT);
		} else {
			blankBar.setHTML("<span style='padding-left: 5px;'>" + statusMessage + "</span>");
			frame.setCellHorizontalAlignment(blankBar, HasHorizontalAlignment.ALIGN_LEFT);
		}		
	}
	
}
