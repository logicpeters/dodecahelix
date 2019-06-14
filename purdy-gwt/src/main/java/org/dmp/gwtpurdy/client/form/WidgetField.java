package org.dmp.gwtpurdy.client.form;

import com.google.gwt.user.client.ui.Widget;

/**
 *   Wraps a widget in a form field for use in a FormCell
 * 
 * @author dpeters
 *
 */
public class WidgetField implements FormField {

	Widget widget;
	
	public WidgetField(Widget w) {
		widget = w;
	}
	
	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public boolean isFixedWidth() {
		return false;
	}

	@Override
	public String getStringValue() {
		return null;
	}

}
