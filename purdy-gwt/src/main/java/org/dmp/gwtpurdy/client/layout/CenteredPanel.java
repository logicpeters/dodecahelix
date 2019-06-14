package org.dmp.gwtpurdy.client.layout;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *   Centers widget on the screen
 *   
 *   (TODO -- center within another widget)
 *   
 * @author dpeters
 *
 */
public class CenteredPanel extends LayoutPanel {

    public CenteredPanel(Widget centeredWidget, int width, int height) {
        
        this.add(centeredWidget);
        
        // center the canvas
        int clientHeight = Window.getClientHeight();
        int clientWidth = Window.getClientWidth();
        
        double topMargin = (clientHeight - height) / 2;
        double leftMargin = (clientWidth - width) / 2;
        this.setWidgetLeftWidth(centeredWidget, leftMargin, Unit.PX, width, Unit.PX);
        this.setWidgetTopHeight(centeredWidget, topMargin, Unit.PX, height, Unit.PX);
    }
    
}
