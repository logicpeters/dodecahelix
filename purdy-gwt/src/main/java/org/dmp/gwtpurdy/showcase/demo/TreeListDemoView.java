package org.dmp.gwtpurdy.showcase.demo;

import java.util.ArrayList;
import java.util.List;

import org.dmp.gwtpurdy.client.base.BaseResource;
import org.dmp.gwtpurdy.client.list.ListItem;
import org.dmp.gwtpurdy.client.list.ListView;
import org.dmp.gwtpurdy.showcase.ShowcaseResources;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.FlowPanel;

public class TreeListDemoView extends FlowPanel implements DemoView {
	
	private ListView listView;

	public TreeListDemoView() {
		this.setStyleName(ShowcaseResources.INSTANCE.showcaseCss().treeListDemoView());
		
		listView = new ListView();
		listView.setWidth("400px");
		listView.setHeight("400px");
		listView.addStyleName(BaseResource.INSTANCE.baseCss().doubleBorder());
		listView.addStyleName(BaseResource.INSTANCE.baseCss().basePadding());
		
		listView.addHeader(SafeHtmlUtils.fromString("My List"));
		
		this.add(listView);
		
		loadList();
	}

	private void loadList() {
	    
	    
	    
	    List<ListItem> list = new ArrayList<ListItem>();
		
	    ListItem userItem = new ListItem("Users");
	    userItem.setIconUrl(ShowcaseResources.INSTANCE.user().getSafeUri().asString());
	    userItem.setSubText(SafeHtmlUtils.fromString("All users in the system"));
	    List<ListItem> childItems = new ArrayList<ListItem>();
	    
	    ListItem userZorroItem = new ListItem("Zorro");
	    userZorroItem.setIconUrl(ShowcaseResources.INSTANCE.userZorro().getSafeUri().asString());
	    childItems.add(userZorroItem);

	    ListItem userPoliceItem = new ListItem("Police");
	    userPoliceItem.setIconUrl(ShowcaseResources.INSTANCE.userPolice().getSafeUri().asString());
	    childItems.add(userPoliceItem);

	    ListItem userFemaleItem = new ListItem("Female");
	    userFemaleItem.setIconUrl(ShowcaseResources.INSTANCE.userFemale().getSafeUri().asString());
	    childItems.add(userFemaleItem);  

	    ListItem userRedItem = new ListItem("Red");
	    userRedItem.setIconUrl(ShowcaseResources.INSTANCE.userRed().getSafeUri().asString());
	    childItems.add(userRedItem); 

	    userItem.setChildItems(childItems);
	    list.add(userItem);
	    
	    for (int i=0; i<20; i++) {
			ListItem item = new ListItem("Test" + i);
			item.setIconUrl(ShowcaseResources.INSTANCE.listItemIcon().getSafeUri().asString());

			if (i==0) {
				ListItem childItem = new ListItem("Child 1");
                ListItem childTwo = new ListItem("Child 2");
                
				List<ListItem> children = new ArrayList<ListItem>();
				children.add(childItem);
				children.add(childTwo);
				item.setChildItems(children);
			}
			
			list.add(item);
		}
		
		listView.loadList(list);
		
	}

	@Override
	public Demo getDemoDefinition() {
		return Demo.LIST;
	}

}
