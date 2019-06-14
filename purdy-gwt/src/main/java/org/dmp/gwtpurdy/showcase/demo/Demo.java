package org.dmp.gwtpurdy.showcase.demo;

import org.dmp.gwtpurdy.showcase.ShowcaseResources;

import com.google.gwt.resources.client.ImageResource;

public enum Demo {
	
	DEFAULT_FORM("Default form", 1), 
	LABELS_ON_TOP("Labels on top", 1), 
	MULTI_COLUMN("Multi-Column", 1),
	DIALOGS("Dialog", 2),
	GRID("Grid", 3),
	STATUS("Status", 4),
	LIST("Tree List", 5);

	private String display;
	private ImageResource icon;

	Demo(String display, int icon) {
		this.display = display;
		switch (icon) {
			case 1 : this.icon = ShowcaseResources.INSTANCE.formIcon(); break;
			case 2 : this.icon = ShowcaseResources.INSTANCE.dialogIcon(); break;
			case 3 : this.icon = ShowcaseResources.INSTANCE.gridIcon(); break;
			case 4 : this.icon = ShowcaseResources.INSTANCE.statusIcon(); break;
			case 5 : this.icon = ShowcaseResources.INSTANCE.listIcon(); break;
		}
		
	}

	public String getDisplayName() {
		return display;
	}

	public String getIconUrl() {
		String iconUrl = null;
		if (icon!=null) {
			iconUrl = icon.getSafeUri().asString();
		}
		return iconUrl;
	}
}
