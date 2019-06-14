package org.dmp.gwtpurdy.client.form;

import com.google.gwt.user.client.ui.TextBox;

/**
 *   Simple label - text field form input
 * 
 * @author dmpuser
 *
 */
public class FormInputField extends TextBox implements FormField {

	private boolean fixedWidth = false;
	
    public FormInputField() {
        this.addStyleName(FormResource.INSTANCE.formCss().formField());
        this.addStyleName(FormResource.INSTANCE.formCss().formFieldGradient());
    }

	public boolean isFixedWidth() {
		return fixedWidth;
	}

	public void setFixedWidth(boolean fixedWidth) {
		this.fixedWidth = fixedWidth;
	}

	@Override
	public String getStringValue() {
		return this.getValue();
	}
    
	/**
	 *   Sets a grey watermark text in a blank input field,
	 *   <br/>which clears when focus is recieved
	 *   
	 *   <br/>Only works in HTML 5 compatible browsers
	 * @param placeholderText
	 */
	public void setPlaceholderText(String placeholderText) {
		// only works in HTML 5 compatible browsers
		this.getElement().setAttribute("placeholder", placeholderText);
	}
}
