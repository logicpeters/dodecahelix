package org.dmp.gwtpurdy.client.text;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface TextResources extends ClientBundle {
	
	public TextResources INSTANCE = GWT.create(TextResources.class);

	@Source("tilemap.txt")
	public TextResource tileMap();
	
}
