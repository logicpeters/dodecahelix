package org.dmp.gwtpurdy.client.form;

import com.google.gwt.user.client.ui.ListBox;

public class FormCombobox extends ListBox implements FormField {
	
	private boolean fixedWidth = false;

    public FormCombobox() {
        this.addStyleName(FormResource.INSTANCE.formCss().formField());
        this.addStyleName(FormResource.INSTANCE.formCss().formFieldGradient());
    }

	@Override
	public boolean isFixedWidth() {
		return fixedWidth;
	}

	public void setFixedWidth(boolean fixedWidth) {
		this.fixedWidth = fixedWidth;
	}

	@Override
	public String getStringValue() {
		String value = null;
		int selectIdx = this.getSelectedIndex();
		if (selectIdx>=0) {
			value = this.getValue(selectIdx);
		}
		return value;
	}
	
	
	
}
