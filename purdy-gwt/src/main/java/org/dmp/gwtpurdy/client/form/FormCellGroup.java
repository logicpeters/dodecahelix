package org.dmp.gwtpurdy.client.form;

import org.dmp.gwtpurdy.client.base.BaseResource;
import org.dmp.gwtpurdy.client.utils.GwtStringUtils;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Groups a set of form cells, arranging in a grid-like interface, applying a
 * header, similar to a fieldset
 * 
 * @author dmpuser
 * 
 */
public class FormCellGroup extends Composite {
	
	private FlowPanel frame;

	private HorizontalPanel groupLabelBar = new HorizontalPanel();
	private HTML groupLabel = new HTML();
	
	// fields that cascade down to the FormCell level
	private String labelWidths;
	private String fieldWidths;
	private LabelPosition labelPositions;

	private int columnCount = 1;
	private int currentColumn = 0;
	private HorizontalPanel currentRow = new HorizontalPanel();

	public FormCellGroup(SafeHtml groupLabelHtml) {
		this(groupLabelHtml, 1);
	}

	public FormCellGroup(SafeHtml groupLabelHtml, int cellColumns) {
		this.columnCount = cellColumns;
		frame = new FlowPanel();

		groupLabelBar.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		groupLabelBar.setWidth("100%");

		groupLabel.setHTML(groupLabelHtml);
		groupLabel.addStyleName(FormResource.INSTANCE.formCss().formGroupLabel());
		groupLabelBar.add(groupLabel);

        // chokes in IE if label is blank
		if (!GwtStringUtils.isBlank(groupLabelHtml.asString())) {
		    // TODO - Ughh - hackish
		    Integer approxLabelWidth = groupLabelHtml.asString().length() * 10;
	        groupLabelBar.setCellWidth(groupLabel, approxLabelWidth.toString() + "px");
		}

		HTML hrHtml = new HTML();
		hrHtml.addStyleName(BaseResource.INSTANCE.baseCss().hrLine());
		groupLabelBar.add(hrHtml);

		frame.add(groupLabelBar);
		frame.add(currentRow);
		
		initWidget(frame);
		this.setStyleName(FormResource.INSTANCE.formCss().formGroup());
	}

	public void setLabelWidths(String l_labelWidths) {
		this.labelWidths = l_labelWidths;
		// set the existing child label widths
		for (int i=0; i<frame.getWidgetCount(); i++) {
			Widget child = frame.getWidget(i);
			if (child instanceof FormCell) {
				((FormCell) child).setLabelWidth(l_labelWidths);
			}
		}
	}

	public void setFieldWidths(String l_fieldWidths) {
		this.fieldWidths = l_fieldWidths;
		// set the existing child field widths
		for (int i=0; i<frame.getWidgetCount(); i++) {
			Widget child = frame.getWidget(i);
			if (child instanceof FormCell) {
				((FormCell) child).setFieldWidth(l_fieldWidths);
			}
		}
	}

	public void addFormCell(FormCell formCell) {

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

		currentRow.add(formCell);
		
		currentColumn += 1;
		if (currentColumn >= columnCount) {
			// insert a new row
			currentRow = new HorizontalPanel();
			currentColumn = 0;
			frame.add(currentRow);
		}

	}

	/**
	 *   Whether to show the grouping label
	 * 
	 * @param show
	 */
	public void setGroupLabelVisible(boolean show) {
		if (show) {
			groupLabelBar.setVisible(true);
		} else {
			groupLabelBar.setVisible(false);
		}
	}
	
	/**
	 *   Allows multiple FormCells to be arranged within a single row 
	 *   
	 * @param columns
	 */
	public void setColumnCount(int columns) {
		this.columnCount = columns;
	}

	/**
	 *   Sets the label position for any FormCells added to this group
	 *   
	 * @param labelPositions
	 */
    public void setLabelPositions(LabelPosition labelPositions) {
        this.labelPositions = labelPositions;
    }

    public String getLabelWidths() {
        return labelWidths;
    }

    public String getFieldWidths() {
        return fieldWidths;
    }

    public LabelPosition getLabelPositions() {
        return labelPositions;
    }

}
