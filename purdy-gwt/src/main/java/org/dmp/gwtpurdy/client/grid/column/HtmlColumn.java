package org.dmp.gwtpurdy.client.grid.column;

import org.dmp.gwtpurdy.client.grid.data.GridRecord;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.cellview.client.Column;

public class HtmlColumn extends Column<GridRecord, SafeHtml> {

    private SafeHtml html;
    
    public HtmlColumn(Cell<SafeHtml> cell, SafeHtml html) {
        super(cell);
        
        this.html = html;
    }

    @Override
    public SafeHtml getValue(GridRecord record) {
        
        return html;
    }

}
