package org.dmp.gwtpurdy.client.list;

import java.util.ArrayList;
import java.util.List;

import org.dmp.gwtpurdy.client.base.BaseResource;
import org.dmp.gwtpurdy.client.color.ColorResource;
import org.dmp.gwtpurdy.client.form.FormResource;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;

/**
 *   Simple scrollable list of items that are selectable
 *   
 * @author dpeters
 *
 */
public class ListView extends Composite implements HasClickHandlers {
	
	private FlowPanel contents;
	private ScrollPanel scrollPanel;
	
	private HTML headerLabel;
	private SafeHtml initialHeader;
	
	private ListItem selectedItem;
	private HTML selectedLabel;
	
	// the full list (including children) from the root level
	private List<ListItem> listItems = new ArrayList<ListItem>();
	
	// parent item for the currently selected list
	private ListItem parentItem;
	
	private String listItemColorCss = ColorResource.INSTANCE.colorCss().colorSchemeLight();
	private String selectedColorCss = ColorResource.INSTANCE.colorCss().colorSchemeBlack();
	
	public ListView() {		
		contents = new FlowPanel();
		scrollPanel = new ScrollPanel(contents);
		headerLabel = new HTML();
		headerLabel.addStyleName(FormResource.INSTANCE.formCss().listViewHeader());
		headerLabel.addStyleName(ColorResource.INSTANCE.colorCss().colorSchemeDarkGrey());
		
	    // header, followed by a scrollpane -- so you don't scroll outside
        FlowPanel frame = new FlowPanel();
        frame.addStyleName(FormResource.INSTANCE.formCss().listView());
		frame.add(headerLabel);
		frame.add(scrollPanel);
		
		// hide header, unless requested (or nested list)
		headerLabel.setVisible(false);
		
		initWidget(frame);
	}
	
	public void setHeight(String height) {
		// TODO -- find a way to set the height of the scroll panel relative to the main panel
		
		//super.setHeight(height);
		scrollPanel.setHeight(height);
	}
	
	public void addHeader(SafeHtml headerHtml) {
	    this.initialHeader = headerHtml;
		headerLabel.setHTML(headerHtml);
		headerLabel.setVisible(true);
		
		headerLabel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (initialHeader!=null) {
                    headerLabel.setText(initialHeader.asString());
                }
                
                loadList(listItems);
            }
		});
	}
	
	private void addItem(final ListItem item) {

		StringBuffer labelHtml = new StringBuffer();
		if (item.getIconUrl()!=null) {
		    labelHtml.append("<img src='" + item.getIconUrl() + "'/>");
		}
		labelHtml.append(item.getDisplay().asString());
		
		if (item.getChildItems()!=null && item.getChildItems().size()>0) {
			labelHtml.append("<img style='float:right' src='" + FormResource.INSTANCE.listItemNavigate().getSafeUri().asString() + "'/>");
		}
		
		if (item.getSubText()!=null) {
		    labelHtml.append("<div class='" + FormResource.INSTANCE.formCss().listItemSubtext() + "'>" + item.getSubText().asString() + "</div>");
		}
		
		final HTML itemLabel = new HTML(labelHtml.toString());
		itemLabel.addStyleName(FormResource.INSTANCE.formCss().listViewItem());
		itemLabel.addStyleName(listItemColorCss);
		
		contents.add(itemLabel);
		
		itemLabel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
			    if (item.getChildItems()!=null && item.getChildItems().size()>0) {
			        navigateDown(item);
			    } else {
			    	selectedItem = item;
    				
    				// highlight this item, and remove highlight from other items in this list
			    	itemLabel.removeStyleName(listItemColorCss);
    				itemLabel.addStyleName(selectedColorCss);
    				
    				// remove highlight from previous selection (if exists)
    				if (selectedLabel!=null) {
    					selectedLabel.removeStyleName(selectedColorCss);
    					selectedLabel.addStyleName(listItemColorCss);
    				}
    				selectedLabel = itemLabel;
    				
    				// fire an event for the ListView, for external components listening for clicks.  They can call the getSelectedItem
    				//  on this objetc to find the clicked item
    				NativeEvent ne = Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false);
    				DomEvent.fireNativeEvent(ne, ListView.this);
			    }
			}
		});
	}

	/**
	 *   Loads the list from the root level (if list items have child elements)
	 * 
	 * @param list
	 */
	public void loadList(List<ListItem> list) {
	    this.listItems = list;
        this.parentItem = null;
	    clearList();
        
	    for (ListItem child : list) {
            this.addItem(child);
        }
	}
	
	public void addItemToCurrentLevel(ListItem item) {
	    parentItem.addChildItem(item);
	    addItem(item);
	}
	
	/**
	 *   Navigate down the tree to the specified list item
	 * 
	 * @param item
	 */
	private void navigateDown(ListItem item) {
	    this.parentItem = item;
        clearList();
        
        for (ListItem child : item.getChildItems()) {
            this.addItem(child);
        }
        
        if (initialHeader!=null) {
            String headerText = headerLabel.getText();
            headerText += "  >  " + item.getDisplay().asString();
            headerLabel.setText(headerText);
            headerLabel.addStyleName(BaseResource.INSTANCE.baseCss().underline());
        }
    }

    public void clearList() {
        contents.clear();
        selectedItem = null;
        selectedLabel = null;        
    }

    @Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addHandler(handler,ClickEvent.getType());
	}

	public ListItem getSelectedItem() {
		return selectedItem;
	}

}
