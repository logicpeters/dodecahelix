package org.dmp.gwtpurdy.client.form;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;

public class FormCheckbox extends HTML implements FormField {

	private boolean checked = false;
	
	public FormCheckbox() {
		super("");
		this.addStyleName(FormResource.INSTANCE.formCss().formField());
		this.addStyleName(FormResource.INSTANCE.formCss().formCheckbox());
		this.addStyleName(FormResource.INSTANCE.formCss().checkboxSpriteUnchecked());
		
		this.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (!checked) {
					check();
				} else {
					uncheck();
				}
			}
			
		});
	}
	
	public void uncheck() {
		FormCheckbox.this.removeStyleName(FormResource.INSTANCE.formCss().checkboxSpriteChecked());
		FormCheckbox.this.addStyleName(FormResource.INSTANCE.formCss().checkboxSpriteUnchecked());
		checked = false;
	}
	
	public void check() {
		FormCheckbox.this.removeStyleName(FormResource.INSTANCE.formCss().checkboxSpriteUnchecked());
		FormCheckbox.this.addStyleName(FormResource.INSTANCE.formCss().checkboxSpriteChecked());
		checked = true;
	}

	public boolean isChecked() {
		return checked;
	}

	@Override
	public boolean isFixedWidth() {
		return true;
	}

	@Override
	public String getStringValue() {
		Boolean checkedBool = new Boolean(isChecked());
		return checkedBool.toString();
	}

}
