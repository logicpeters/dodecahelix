package org.dmp.gwtpurdy.showcase.demo;

import org.dmp.gwtpurdy.client.button.ButtonBar;
import org.dmp.gwtpurdy.client.button.GradientButton;
import org.dmp.gwtpurdy.client.form.FormCell;
import org.dmp.gwtpurdy.client.form.FormCellGroup;
import org.dmp.gwtpurdy.client.form.FormInputField;
import org.dmp.gwtpurdy.client.form.FormView;
import org.dmp.gwtpurdy.client.form.LabelPosition;
import org.dmp.gwtpurdy.client.images.ImgResources;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class MultiColumnDemoView extends FormView implements DemoView {
	
	private GradientButton submitButton;
	
	public MultiColumnDemoView() {
		super();
        this.setWidth("100%");
        
        FormCellGroup baseDemoGroup = new FormCellGroup(SafeHtmlUtils.fromString("Multi Column"),2);
        baseDemoGroup.setFieldWidths("200px");
        baseDemoGroup.setGroupLabelVisible(true);
        baseDemoGroup.setLabelPositions(LabelPosition.TOP);
        
        FormCell firstNameField = new FormCell(new FormInputField(), SafeHtmlUtils.fromString("First Name:"));
        firstNameField.setLabelHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        firstNameField.setSubText(SafeHtmlUtils.fromString("Please enter your first name"));
        baseDemoGroup.addFormCell(firstNameField);
        
        FormCell lastNameField = new FormCell(new FormInputField(), SafeHtmlUtils.fromString("Last Name:"));
        lastNameField.setLabelHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        lastNameField.setSubText(SafeHtmlUtils.fromString("Please enter your last name"));
        baseDemoGroup.addFormCell(lastNameField);
        
        this.appendCellGroup(baseDemoGroup);
        
        submitButton = new GradientButton("Submit");
        //submitButton.setColorScheme(ColorScheme.LIGHT_RED);
        
        ButtonBar buttonBar = new ButtonBar(submitButton);
        buttonBar.addButton(new GradientButton(ImgResources.INSTANCE.cancelIcon(),"Cancel"));
        this.appendWidget(buttonBar);
	}

	@Override
	public Demo getDemoDefinition() {
		return Demo.MULTI_COLUMN;
	}

}
