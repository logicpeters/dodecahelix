package org.dmp.gwtpurdy.client.form;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;

public class FormView extends Composite {
    
	private FormPanel form;
    private FlowPanel contentPanel;
    
    private String labelWidths;
    private String fieldWidths;
    private LabelPosition labelPositions;
    
    public FormView() {
        FormResource.INSTANCE.formCss().ensureInjected();
        form = new FormPanel();
        contentPanel = new FlowPanel();
        form.setWidget(contentPanel);
        
        initWidget(form);
        
        this.setStyleName(FormResource.INSTANCE.formCss().formView());        
    }

    public void setLabelWidths(String labelWidths) {
        this.labelWidths = labelWidths;
    }

    public void setFieldWidths(String fieldWidths) {
        this.fieldWidths = fieldWidths;
    }

    public void setLabelPositions(LabelPosition labelPositions) {
        this.labelPositions = labelPositions;
    }

    /**
     *   Adds a cell group and configures with form-level label widths, field widths and label positions
     * 
     * @param group
     */
    public void appendCellGroup(FormCellGroup group) {
        if (labelPositions!=null && group.getLabelPositions()==null) {
            group.setLabelPositions(labelPositions);
        }
        if (fieldWidths!=null && group.getFieldWidths()==null) {
            group.setFieldWidths(fieldWidths);
        }
        if (labelWidths!=null && group.getLabelWidths()==null) {
            group.setLabelWidths(labelWidths);
        }
        contentPanel.add(group);
    }
    
    /**
     *   Adds a form cell, outside of a group.  Sets label widths, field width and label positions
     * 
     * @param formCell
     */
    public void appendFormCell(FormCell formCell) {

        if (labelWidths != null) {
            formCell.setLabelWidth(labelWidths);
        }
        if (fieldWidths != null) {
            if (!formCell.getFieldWidget().isFixedWidth()) {
                formCell.setFieldWidth(fieldWidths);
            }
        }
        if (labelPositions!=null) {
            formCell.setLabelPosition(labelPositions);
        }
        contentPanel.add(formCell);
    }
    
    public void appendWidget(Widget w) {
    	contentPanel.add(w);
    }
    
}
