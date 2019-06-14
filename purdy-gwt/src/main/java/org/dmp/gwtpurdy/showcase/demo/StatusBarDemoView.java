package org.dmp.gwtpurdy.showcase.demo;

import org.dmp.gwtpurdy.client.base.component.StatusBar;
import org.dmp.gwtpurdy.client.color.ColorScheme;
import org.dmp.gwtpurdy.client.form.FormCell;
import org.dmp.gwtpurdy.client.form.FormCellGroup;
import org.dmp.gwtpurdy.client.form.FormView;
import org.dmp.gwtpurdy.client.form.LabelPosition;
import org.dmp.gwtpurdy.client.form.WidgetField;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

public class StatusBarDemoView extends FormView implements DemoView {
	
	public StatusBarDemoView() {
        super();
        this.setWidth("100%");
        
        FormCellGroup baseDemoGroup = new FormCellGroup(SafeHtmlUtils.fromString("Status Bars"));
        baseDemoGroup.setGroupLabelVisible(true);
        baseDemoGroup.setLabelWidths("150px");
        baseDemoGroup.setLabelPositions(LabelPosition.LEFT_LEFT);
        this.appendCellGroup(baseDemoGroup);
        
        StatusBar statusOne = new StatusBar();
        statusOne.setWidth(200, Unit.PX);
        statusOne.setStatus(30, 80, "30 / 80", ColorScheme.LIGHT_BLUE);
        FormCell statusOneCell = new FormCell(new WidgetField(statusOne), "Quota Goal:");
        baseDemoGroup.addFormCell(statusOneCell);
        
        StatusBar statusTwo = new StatusBar();
        statusTwo.setWidth(200, Unit.PX);
        statusTwo.setStatus(80, 100, "80%", ColorScheme.LIGHT_RED);
        FormCell statusTwoCell = new FormCell(new WidgetField(statusTwo), "Percent Complete:");
        baseDemoGroup.addFormCell(statusTwoCell);
	}

	@Override
	public Demo getDemoDefinition() {
		return Demo.STATUS;
	}

}
