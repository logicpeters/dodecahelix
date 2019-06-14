package org.dmp.gwtpurdy.client.grid;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.CellTable.Resources;

public interface GridResource extends Resources {

    public CellTable.Resources INSTANCE = GWT.create(GridResource.class);

	/**
	 * The styles used in this widget.
	 */
	@Source("grid.css")
	CellTable.Style cellTableStyle();
	
    @Source("ajax-loader.gif")
	public ImageResource waitSpinner();
	
}
