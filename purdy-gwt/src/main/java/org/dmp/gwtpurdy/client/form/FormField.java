package org.dmp.gwtpurdy.client.form;

import com.google.gwt.user.client.ui.IsWidget;

public interface FormField extends IsWidget {

	public boolean isFixedWidth();
	public String getStringValue();
}
