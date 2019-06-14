package org.dmp.gwtpurdy.client.form;

import com.google.gwt.user.client.ui.PasswordTextBox;

public class FormPasswordField extends PasswordTextBox implements FormField {

    private boolean fixedWidth = false;
    
    public FormPasswordField() {
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

}
