package org.dmp.gwtpurdy.showcase.demo;

import org.dmp.gwtpurdy.client.form.FormCell;
import org.dmp.gwtpurdy.client.form.FormInputField;
import org.dmp.gwtpurdy.client.form.FormPasswordField;
import org.dmp.gwtpurdy.client.form.FormView;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class LoginForm extends FormView {
    
    private FormCell nameField;
    private FormCell passwordField;
    
    public LoginForm() {
        this.setLabelWidths("100px");
        this.setFieldWidths("200px");
        
        nameField = new FormCell(new FormInputField(), SafeHtmlUtils.fromString("Name:"));
        nameField.setLabelHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        nameField.setSubText(SafeHtmlUtils.fromString("Please enter your login name"));
        
        this.appendFormCell(nameField);
        
        passwordField = new FormCell(new FormPasswordField(), SafeHtmlUtils.fromString("Password:"));
        passwordField.setLabelHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        passwordField.setSubText(SafeHtmlUtils.fromString("Please enter your password"));
        
        this.appendFormCell(passwordField);
    }

}
