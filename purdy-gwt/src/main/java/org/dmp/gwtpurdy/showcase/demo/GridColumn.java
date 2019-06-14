package org.dmp.gwtpurdy.showcase.demo;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

public enum GridColumn {

	NAME ("Name"),
	CATEGORY ("Category"),
	DEPARTMENT ("Department"), 
	USER_IMG ("Icon");
	
	private String header;
	
	GridColumn(String header) {
		this.header = header;
	}

	public SafeHtml getHeader() {
		return SafeHtmlUtils.fromString(header);
	}
	
}
