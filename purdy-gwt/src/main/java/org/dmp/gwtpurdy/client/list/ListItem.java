package org.dmp.gwtpurdy.client.list;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

public class ListItem {

    private String value;
    private SafeHtml display;
    private String iconUrl;
    private SafeHtml subText;
    
    private List<ListItem> childItems;

    public ListItem(String value) {
        this.value = value;
        this.display = SafeHtmlUtils.fromString(value);
    }

    public SafeHtml getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = SafeHtmlUtils.fromString(display);
    }

    public void setDisplay(SafeHtml display) {
        this.display = display;
    }

    public String getValue() {
        return value;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public SafeHtml getSubText() {
        return subText;
    }

    public void setSubText(SafeHtml subText) {
        this.subText = subText;
    }

	public List<ListItem> getChildItems() {
		return childItems;
	}

	public void setChildItems(List<ListItem> childItems) {
		this.childItems = childItems;
	}
	
	public void addChildItem(ListItem child) {
	    if (childItems==null) {
	        childItems = new ArrayList<ListItem>();
	    }
	    childItems.add(child);
	}

}
