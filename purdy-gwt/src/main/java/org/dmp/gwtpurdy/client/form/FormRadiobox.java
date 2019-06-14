package org.dmp.gwtpurdy.client.form;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;

public class FormRadiobox extends HTML {
	
	private String value;
	private boolean checked;
	
	public FormRadiobox(String value) {
		
		this.value = value;
		this.addStyleName(FormResource.INSTANCE.formCss().formField());
		this.addStyleName(FormResource.INSTANCE.formCss().formCheckbox());
		this.addStyleName(FormResource.INSTANCE.formCss().radioboxSpriteUnchecked());
		
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
		FormRadiobox.this.removeStyleName(FormResource.INSTANCE.formCss().radioboxSpriteChecked());
		FormRadiobox.this.addStyleName(FormResource.INSTANCE.formCss().radioboxSpriteUnchecked());
		checked = false;
	}
	
	public void check() {
		FormRadiobox.this.removeStyleName(FormResource.INSTANCE.formCss().radioboxSpriteUnchecked());
		FormRadiobox.this.addStyleName(FormResource.INSTANCE.formCss().radioboxSpriteChecked());
		checked = true;
	}

	public boolean isChecked() {
		return checked;
	}

	public String getValue() {
		return value;
	}

}
