package org.dmp.gwtpurdy.client.form;

import com.google.gwt.user.client.ui.TextArea;

public class FormTextArea extends TextArea implements FormField {

	public FormTextArea() {
        this.addStyleName(FormResource.INSTANCE.formCss().formField());
        this.addStyleName(FormResource.INSTANCE.formCss().formFieldGradient());
	}

	@Override
	public boolean isFixedWidth() {
		return true;
	}

	@Override
	public String getStringValue() {
		return this.getValue();
	}
	
}
