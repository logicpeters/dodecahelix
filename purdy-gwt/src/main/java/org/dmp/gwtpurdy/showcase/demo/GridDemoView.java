package org.dmp.gwtpurdy.showcase.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dmp.gwtpurdy.client.grid.FixedHeaderGrid;
import org.dmp.gwtpurdy.client.grid.column.ColumnConfig;
import org.dmp.gwtpurdy.client.grid.column.ImageResourceColumn;
import org.dmp.gwtpurdy.client.grid.data.GridRecord;
import org.dmp.gwtpurdy.showcase.ShowcaseResources;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class GridDemoView extends FlowPanel implements DemoView {

	private FixedHeaderGrid gridFrame;
	
    private enum USER_IMG_REF {
        USER,
        FEMALE,
        RED,
        POLICE,
        ZORRO
    }

    public GridDemoView() {
        this.setStyleName(ShowcaseResources.INSTANCE.showcaseCss().showcaseDemoView());

        ColumnConfig nameColumn = new ColumnConfig(GridColumn.NAME.name(), null, GridColumn.NAME.getHeader(), 150, Unit.PX);
        ColumnConfig categoryColumn = new ColumnConfig(GridColumn.CATEGORY.name(), null, GridColumn.CATEGORY.getHeader(), 100, Unit.PX);

        ColumnConfig departmentColumn = new ColumnConfig(GridColumn.DEPARTMENT.name());
        departmentColumn.setHeader(GridColumn.DEPARTMENT.getHeader());

        ImageResourceColumn imgColumn = new ImageResourceColumn(GridColumn.USER_IMG.name(), loadDemoImages());
        ColumnConfig userImgColumn = new ColumnConfig(GridColumn.USER_IMG.name(), imgColumn,
                SafeHtmlUtils.fromString(""), 40, Unit.PX);
        
        gridFrame = new FixedHeaderGrid(10, true);
        gridFrame.setHeight("200px");
        
        gridFrame.addColumn(nameColumn);
        gridFrame.addColumn(userImgColumn);
        gridFrame.addColumn(categoryColumn);
        gridFrame.addColumn(departmentColumn);
        
        this.add(gridFrame);

        loadDemoValues();
    }

    private void loadDemoValues() {
        List<GridRecord> demoValues = new ArrayList<GridRecord>();

        demoValues.add(new GridRecord(new String[][] { { "NAME", "Ben Dover" }, { "CATEGORY", "Citizen" },
                { "DEPARTMENT", "Customs" }, {"USER_IMG", "USER"} }));
        demoValues.add(new GridRecord(new String[][] { { "NAME", "Ralph Freemon" }, { "CATEGORY", "Citizen" },
                { "DEPARTMENT", "Customs" }, {"USER_IMG", "ZORRO"} }));
        demoValues.add(new GridRecord(new String[][] { { "NAME", "Shaka Zulu" }, { "CATEGORY", "Executive" },
                { "DEPARTMENT", "Human Resources" }, {"USER_IMG", "USER"} }));
        demoValues.add(new GridRecord(new String[][] { { "NAME", "Gorf Nyord" }, { "CATEGORY", "Worker" },
                { "DEPARTMENT", "Assembly" }, {"USER_IMG", "USER"} }));
        demoValues.add(new GridRecord(new String[][] { { "NAME", "Drake Nyaz" }, { "CATEGORY", "Citizen" },
                { "DEPARTMENT", "Customs" }, {"USER_IMG", "POLICE"} }));
        demoValues.add(new GridRecord(new String[][] { { "NAME", "Tinka Fyord" }, { "CATEGORY", "Executive" },
                { "DEPARTMENT", "Sales" }, {"USER_IMG", "FEMALE"} }));
        demoValues.add(new GridRecord(new String[][] { { "NAME", "Graak the Barber" }, { "CATEGORY", "Worker" },
                { "DEPARTMENT", "Assembly" }, {"USER_IMG", "USER"} }));
        demoValues.add(new GridRecord(new String[][] { { "NAME", "Geppo Slacken" }, { "CATEGORY", "Citizen" },
                { "DEPARTMENT", "Development" }, {"USER_IMG", "USER"} }));
        demoValues.add(new GridRecord(new String[][] { { "NAME", "Naniz Shordrik" }, { "CATEGORY", "Worker" },
                { "DEPARTMENT", "Assembly" }, {"USER_IMG", "USER"} }));
        demoValues.add(new GridRecord(new String[][] { { "NAME", "Clowniegagger" }, { "CATEGORY", "Executive" },
                { "DEPARTMENT", "Sales" }, {"USER_IMG", "USER"} }));
        demoValues.add(new GridRecord(new String[][] { { "NAME", "Fred Windbag" }, { "CATEGORY", "Worker" },
                { "DEPARTMENT", "Assembly" }, {"USER_IMG", "RED"} }));
        demoValues.add(new GridRecord(new String[][] { { "NAME", "Tiny Tim Johanssen" }, { "CATEGORY", "Worker" },
                { "DEPARTMENT", "Sales" }, {"USER_IMG", "USER"} }));
        demoValues.add(new GridRecord(new String[][] { { "NAME", "Stinky Smith" }, { "CATEGORY", "Citizen" },
                { "DEPARTMENT", "Customs" }, {"USER_IMG", "ZORRO"} }));

        gridFrame.addRecords(demoValues);
    }

    private Map<String, ImageResource> loadDemoImages() {
        Map<String, ImageResource> images = new HashMap<String, ImageResource>();
        
        images.put(USER_IMG_REF.USER.name(), ShowcaseResources.INSTANCE.user());
        images.put(USER_IMG_REF.FEMALE.name(), ShowcaseResources.INSTANCE.userFemale());
        images.put(USER_IMG_REF.RED.name(), ShowcaseResources.INSTANCE.userRed());
        images.put(USER_IMG_REF.POLICE.name(), ShowcaseResources.INSTANCE.userPolice());
        images.put(USER_IMG_REF.ZORRO.name(), ShowcaseResources.INSTANCE.userZorro());
        
        return images;
    }
    
    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public Demo getDemoDefinition() {
        return Demo.GRID;
    }

}
