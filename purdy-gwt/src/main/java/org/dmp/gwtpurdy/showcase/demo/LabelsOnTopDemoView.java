package org.dmp.gwtpurdy.showcase.demo;

import org.dmp.gwtpurdy.client.button.ButtonBar;
import org.dmp.gwtpurdy.client.button.GradientButton;
import org.dmp.gwtpurdy.client.form.FormCell;
import org.dmp.gwtpurdy.client.form.FormCellGroup;
import org.dmp.gwtpurdy.client.form.FormInputField;
import org.dmp.gwtpurdy.client.form.FormTextArea;
import org.dmp.gwtpurdy.client.form.FormView;
import org.dmp.gwtpurdy.client.form.LabelPosition;
import org.dmp.gwtpurdy.client.images.ImgResources;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Button;


public class LabelsOnTopDemoView extends FormView implements DemoView {
	
	private Button submitButton;
	
	public LabelsOnTopDemoView() {
		super();
        this.setWidth("100%");
		
        FormCellGroup baseDemoGroup = new FormCellGroup(SafeHtmlUtils.fromString("Labels On Top"));
        //baseDemoGroup.setLabelWidths("100px");
        baseDemoGroup.setFieldWidths("200px");
        baseDemoGroup.setGroupLabelVisible(true);
        
        FormCell nameField = new FormCell(new FormInputField(), SafeHtmlUtils.fromString("Name:"));
        nameField.setLabelPosition(LabelPosition.TOP);
        nameField.setSubText(SafeHtmlUtils.fromString("Please enter your name (last, first)"));
        baseDemoGroup.addFormCell(nameField);
        
        FormTextArea textArea = new FormTextArea();
        textArea.setCharacterWidth(50);
        textArea.setHeight("80px");
        FormCell textAreaCell = new FormCell(textArea, SafeHtmlUtils.fromString("Description:"));
        textAreaCell.setLabelPosition(LabelPosition.TOP);
        textAreaCell.setSubText(SafeHtmlUtils.fromString("Please describe yourself (100 words or less)"));
        baseDemoGroup.addFormCell(textAreaCell);
        
        this.appendCellGroup(baseDemoGroup);
        
        submitButton = new GradientButton("Submit");
        ButtonBar buttonBar = new ButtonBar(submitButton);
        buttonBar.setIndent("10px");
        buttonBar.addButton(new GradientButton(ImgResources.INSTANCE.cancelIcon(),"Cancel"));
        this.appendWidget(buttonBar);
	}

	@Override
	public Demo getDemoDefinition() {
		return Demo.LABELS_ON_TOP;
	}

}
