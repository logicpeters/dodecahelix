package org.dmp.gwtpurdy.client.form;

import org.dmp.gwtpurdy.client.utils.GwtStringUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 *   Represents a row in a form (or cell, if the form has multiple columns),
 *   wrapping a single form field widget (i.e; TextBox, TextArea)
 *   
 *   Support for multiple fields per cell forthcoming
 *   
 * 
 * @author dmpuser
 *
 */
public class FormCell extends Composite {
    
	private VerticalPanel frame;
    private FormField formField;
    
    private boolean labelSet = false;
    private LabelPosition labelPosition = LabelPosition.LEFT_RIGHT;
    private ValidationPosition validationPosition = ValidationPosition.SUBTEXT;
    
    private HorizontalPanel labelFieldPanel = new HorizontalPanel();
    private HorizontalPanel supertextPanel = new HorizontalPanel();
    private HorizontalPanel subtextPanel = new HorizontalPanel();
    
    private SafeHtml labelHtml;
    
    private boolean invalid = false;
    
    // text to restore when clearing validation error
    private String restoreText = null;
    
    private HTML label = new HTML();
    private HTML superText = new HTML();
    private HTML subText = new HTML();
    private HTML rightText = new HTML();
    private HTML supertextIndent = new HTML();
    private HTML subtextIndent = new HTML();
    
    public FormCell(FormField formField, String html) {
    	this(formField, SafeHtmlUtils.fromString(html));
    }
    
    public FormCell(FormField formField, SafeHtml labelHtml) {
    	frame = new VerticalPanel();
    	
        this.labelHtml = labelHtml;
        labelFieldPanel.addStyleName(FormResource.INSTANCE.formCss().formField());
        label.setHTML(labelHtml);
        label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        label.addStyleName(FormResource.INSTANCE.formCss().formLabel());
        labelFieldPanel.add(label);
        labelFieldPanel.setCellVerticalAlignment(label, HasVerticalAlignment.ALIGN_MIDDLE);
        labelFieldPanel.add(formField);
        
        superText.addStyleName(FormResource.INSTANCE.formCss().formCellSubtext());
        supertextPanel.add(supertextIndent);
        supertextPanel.add(superText);
        
        subText.addStyleName(FormResource.INSTANCE.formCss().formCellSubtext());
        subtextPanel.add(subtextIndent);
        subtextPanel.add(subText);
        
        this.formField = formField;
        
        frame.add(supertextPanel);
        frame.add(labelFieldPanel);
        frame.add(subtextPanel);
        
        initWidget(frame);
        
        this.addStyleName(FormResource.INSTANCE.formCss().formCell());
    }
    
    public void setValidationPostion(ValidationPosition position) {
    	if (ValidationPosition.SUBTEXT.equals(position)
    			&& LabelPosition.BOTTOM.equals(labelPosition)) {
    		GWT.log("Cannot set validation position in the same location as the label.  Moving validation position to the top.");
    		position = ValidationPosition.SUPERTEXT;
    	}
    	if (ValidationPosition.SUPERTEXT.equals(position)
    			&& LabelPosition.TOP.equals(labelPosition)) {
    		GWT.log("Cannot set validation position in the same location as the label.  Moving validation position to the bottom.");
    		position = ValidationPosition.SUBTEXT;
    	}
    	this.validationPosition = position;
    }
    
    public void setLabelPosition(LabelPosition position) {
    	if (labelSet) {
    		GWT.log("Label position cannot be set twice!");
    	} else {
    		labelSet = true;
    		switch(position) {
    			case LEFT_LEFT: setLabelLeftLeft(); break;
    			case TOP : setLabelTop(); break;
    			case BOTTOM : setLabelBottom(); break;
    			case RIGHT : setLabelRight(); break;
    		}
            this.labelPosition = position;
    	}
    }
    
    private void setLabelRight() {
    	label.setHTML("");
    	rightText.setHTML(labelHtml); 
    	labelFieldPanel.add(rightText); 
    	labelFieldPanel.setCellVerticalAlignment(rightText, HasVerticalAlignment.ALIGN_MIDDLE);
	}

	private void setLabelBottom() {
		label.setHTML("");
		subText.addStyleName(FormResource.INSTANCE.formCss().formLabel());
		subText.setHTML(labelHtml);
		if (ValidationPosition.SUBTEXT.equals(validationPosition)) {
			GWT.log("Validation position cannot be in the same location as the label.  Switching validation position to the top.");
			validationPosition = ValidationPosition.SUPERTEXT;
		}
	}

	private void setLabelTop() {		
		label.setHTML("");
		superText.addStyleName(FormResource.INSTANCE.formCss().formLabel());
		superText.setHTML(labelHtml);
		if (ValidationPosition.SUPERTEXT.equals(validationPosition)) {
			GWT.log("Validation position cannot be in the same location as the label.  Switching validation position to the bottom.");
			validationPosition = ValidationPosition.SUBTEXT;
		}
	}

	private void setLabelLeftLeft() {
		label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
	}

	public void setSubText(SafeHtml html) {
        subText.setHTML(html);
    }
    
    public void setSuperText(SafeHtml html) {
        superText.setHTML(html);
    }
    
    public void setLabelWidth(String width) {
        label.setWidth(width);
        supertextIndent.setWidth(width);
        subtextIndent.setWidth(width);
    }
    
    public void setFieldWidth(String width) {
        formField.asWidget().setWidth(width);
    }
    
    public void setLabelHorizontalAlignment(HorizontalAlignmentConstant align) {
        label.setHorizontalAlignment(align);
    }

    public FormField getFieldWidget() {
        return formField;
    }

	public void invalidate(String message) {

		invalid = true;
		HTML validationTextComponent = null;
		switch (validationPosition) {
			case SUPERTEXT: validationTextComponent = superText; break;
			case SUBTEXT: validationTextComponent = subText; break;
		}
		validationTextComponent.addStyleName(FormResource.INSTANCE.formCss().formFieldValidationError());
		validationTextComponent.removeStyleName(FormResource.INSTANCE.formCss().formFieldValid());
		if (GwtStringUtils.isBlank(validationTextComponent.getText())) {
			validationTextComponent.setHTML(message);
		} else {
			String currentText = validationTextComponent.getHTML();
			restoreText = currentText;
			if (!GwtStringUtils.isBlank(message) && !currentText.contains(message)) {
				currentText = message + "  " + currentText;
				validationTextComponent.setHTML(currentText);
			}
		}

	}

	public void clearValidationError() {
		if (invalid) {
			HTML validationTextComponent = null;
			switch (validationPosition) {
				case SUPERTEXT: validationTextComponent = superText; break;
				case SUBTEXT: validationTextComponent = subText; break;
			}
			validationTextComponent.addStyleName(FormResource.INSTANCE.formCss().formFieldValid());
			validationTextComponent.removeStyleName(FormResource.INSTANCE.formCss().formFieldValidationError());
			validationTextComponent.setHTML(restoreText);
			invalid = false;
		}
	}

	public SafeHtml getLabelHtml() {
		return labelHtml;
	}
    
	
}
