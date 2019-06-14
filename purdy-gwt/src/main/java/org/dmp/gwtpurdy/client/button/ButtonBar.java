package org.dmp.gwtpurdy.client.button;

import java.util.Iterator;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ButtonBar extends HorizontalPanel {

    private HorizontalAlignmentConstant horizontalAlignment;
    
	private HTML indent = new HTML();
	
	public ButtonBar() {
		this.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		this.setSpacing(10);
		this.add(indent);
	}
	
	public ButtonBar(Button button) {
		this();
		addButton(button);
	}

	public void setIndent(String indentWidth) {
		indent.setWidth(indentWidth);
	}
	
	public void addButton(Button button) {
		this.add(button);
		if (horizontalAlignment!=null) {
		    this.setCellHorizontalAlignment(button,  horizontalAlignment);
		}
	}
	
	public void centerButtons() {
	    this.setSpacing(0);
	    this.setWidth("100%");
	    Iterator<Widget> buttons = this.getChildren().iterator();
	    while (buttons.hasNext()) {
	        Widget button = buttons.next();
	        this.setCellHorizontalAlignment(button, HasHorizontalAlignment.ALIGN_CENTER);
	    }
	    
	    this.horizontalAlignment = HasHorizontalAlignment.ALIGN_CENTER;
	}

}
