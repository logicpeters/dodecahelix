package org.dmp.gwtpurdy.showcase.demo;

import org.dmp.gwtpurdy.client.base.Alignment;
import org.dmp.gwtpurdy.client.button.ButtonBar;
import org.dmp.gwtpurdy.client.button.GradientButton;
import org.dmp.gwtpurdy.client.form.FormCell;
import org.dmp.gwtpurdy.client.form.FormCellGroup;
import org.dmp.gwtpurdy.client.form.FormCheckbox;
import org.dmp.gwtpurdy.client.form.FormCombobox;
import org.dmp.gwtpurdy.client.form.FormInputField;
import org.dmp.gwtpurdy.client.form.FormTextArea;
import org.dmp.gwtpurdy.client.form.FormView;
import org.dmp.gwtpurdy.client.form.LabelPosition;
import org.dmp.gwtpurdy.client.form.RadioboxGroup;
import org.dmp.gwtpurdy.client.images.ImgResources;
import org.dmp.gwtpurdy.showcase.demo.DefaultFormDemoPresenter.Display;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class DefaultFormDemoView extends FormView implements Display, DemoView {
	
	private GradientButton submitButton;
	private FormCell nameField;
	private FormCell descriptionField;
	private FormCell genderField;
	
	public DefaultFormDemoView() {
		super();
        this.setWidth("100%");
        
        FormCellGroup baseDemoGroup = new FormCellGroup(SafeHtmlUtils.fromString("Default Form"));
        baseDemoGroup.setLabelWidths("100px");
        baseDemoGroup.setFieldWidths("200px");
        baseDemoGroup.setGroupLabelVisible(true);
        
        nameField = new FormCell(new FormInputField(), SafeHtmlUtils.fromString("Name:"));
        nameField.setLabelHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        nameField.setSubText(SafeHtmlUtils.fromString("Please enter your name (last, first)"));
        baseDemoGroup.addFormCell(nameField);
        
        FormTextArea description = new FormTextArea();
        description.setCharacterWidth(50);
        description.setHeight("80px");
        descriptionField = new FormCell(description, SafeHtmlUtils.fromString("Description:"));
        descriptionField.setSubText(SafeHtmlUtils.fromString("Please describe yourself (100 words or less)"));
        baseDemoGroup.addFormCell(descriptionField);
        
        FormCheckbox checkbox = new FormCheckbox();
        FormCell sourceField = new FormCell(checkbox, SafeHtmlUtils.fromString("Registered to Vote?"));
        sourceField.setLabelPosition(LabelPosition.RIGHT);
        baseDemoGroup.addFormCell(sourceField);
        
        FormCombobox typeCombobox = new FormCombobox();
        typeCombobox.addItem("Criminal");
        typeCombobox.addItem("Worker");
        typeCombobox.addItem("Citizen");
        typeCombobox.addItem("Executive");
        
        FormCell typeField = new FormCell(typeCombobox, SafeHtmlUtils.fromString("Category:"));
        typeField.setLabelHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        typeField.setSubText(SafeHtmlUtils.fromString("Please enter your citizen category"));
        baseDemoGroup.addFormCell(typeField);

        RadioboxGroup genderGroup = new RadioboxGroup(Alignment.VERTICAL);
        genderGroup.addRadioBoxEntry("male", SafeHtmlUtils.fromString("Male"));
        genderGroup.addRadioBoxEntry("female", SafeHtmlUtils.fromString("Female"));
        genderField = new FormCell(genderGroup, SafeHtmlUtils.fromString("Gender:"));
        baseDemoGroup.addFormCell(genderField);        
        
        this.appendCellGroup(baseDemoGroup);
        
        submitButton = new GradientButton("Submit");
        ButtonBar buttonBar = new ButtonBar(submitButton);
        buttonBar.setIndent("105px");
        buttonBar.addButton(new GradientButton(ImgResources.INSTANCE.cancelIcon(),"Cancel"));
        this.appendWidget(buttonBar);
	}

	@Override
	public Demo getDemoDefinition() {
		return Demo.DEFAULT_FORM;
	}

	@Override
	public HasClickHandlers getSubmitButton() {
		return submitButton;
	}

	@Override
	public String getNameValue() {
		return nameField.getFieldWidget().getStringValue();
	}

	@Override
	public void invalidateName(String message) {
		nameField.invalidate(message);		
	}

	@Override
	public void clearValidationErrors() {
		nameField.clearValidationError();	
		genderField.clearValidationError();
		descriptionField.clearValidationError();
	}

    @Override
    public void invalidateGender(String text) {
        genderField.invalidate(text);
    }

    @Override
    public String getDescriptionValue() {
        return descriptionField.getFieldWidget().getStringValue();
    }

    @Override
    public String getGenderValue() {
        return genderField.getFieldWidget().getStringValue();
    }

    @Override
    public void invalidateDescription(String text) {
        descriptionField.invalidate(text);
    }

}
