package org.dmp.gwtpurdy.client.grid;

import java.util.List;

import org.dmp.gwtpurdy.client.base.BaseResource;
import org.dmp.gwtpurdy.client.grid.column.ColumnConfig;
import org.dmp.gwtpurdy.client.grid.data.GridRecord;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 *   A styled grid with a fixed header (does not scroll, when content scrolls)
 *   and paging controls
 *   
 * @author dpeters
 *
 */
public class FixedHeaderGrid extends Composite {
    
    private FlowPanel flowPanel = new FlowPanel();
    
    private GridHeader headerGrid;
    private ScrollPanel headerGridPanel;
    private GridContent contentGrid;
    private ScrollPanel contentGridPanel;
    
    private SimplePanel pagerPanel;
    
    public FixedHeaderGrid(int pageSize, boolean hasCheckboxColumn) {
        
        contentGridPanel = new ScrollPanel();
        contentGridPanel.setStyleName(BaseResource.INSTANCE.baseCss().fixedHeaderGridContentPanel());
        
        contentGrid = new GridContent(pageSize, hasCheckboxColumn);
        contentGridPanel.setWidget(contentGrid);
        
        // Does this have to be a scroll panel?
        headerGridPanel = new ScrollPanel();
        headerGridPanel.setStyleName(BaseResource.INSTANCE.baseCss().fixedHeaderGridHeaderPanel());
        
        headerGrid = new GridHeader(this, hasCheckboxColumn);
        headerGridPanel.setWidget(headerGrid);
        
        flowPanel.add(headerGridPanel);
        flowPanel.add(contentGridPanel);

        this.initWidget(flowPanel);         
        
        this.addPagerControl();
    }
        
    private void addPagerControl() {
        // add a pager
        SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
        SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
        contentGrid.addPagerControl(pager);
        
        // TODO -- make this a Horizontal Panel so that dev can control the alignment/location of pager
        pagerPanel = new SimplePanel(pager);
        pagerPanel.setStyleName(BaseResource.INSTANCE.baseCss().fixedHeaderGridPagerPanel());
        
        flowPanel.add(pagerPanel); 
    }
    
    public void setHeight(String height) {
        contentGridPanel.setHeight(height);
        super.setHeight(height);
    }
    
    public void addColumn(final ColumnConfig columnConfig) {
        headerGrid.addColumn(columnConfig);
        
        columnConfig.setHeader(null);
        contentGrid.addColumn(columnConfig);
    }

    public GridContent getGridView() {
        return contentGrid;
    }

    public void checkAllColumns(boolean checked) {
        contentGrid.checkAllColumns(checked);
    }   
    
    public void sortColumn(int i, boolean sortAscending) {
        contentGrid.sortColumn(i, sortAscending);
    }

	public void addRecords(List<GridRecord> demoValues) {
		contentGrid.addRecords(demoValues);		
	}

}
