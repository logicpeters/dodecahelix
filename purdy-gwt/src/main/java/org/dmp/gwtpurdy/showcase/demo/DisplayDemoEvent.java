package org.dmp.gwtpurdy.showcase.demo;


import com.google.gwt.event.shared.GwtEvent;

public class DisplayDemoEvent extends GwtEvent<DisplayDemoEventHandler> {

	public static Type<DisplayDemoEventHandler> TYPE = new Type<DisplayDemoEventHandler>();

	private Demo demo;

	public DisplayDemoEvent(Demo requestedDemo) {
		this.demo = requestedDemo;
	}

	@Override
	public Type<DisplayDemoEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DisplayDemoEventHandler handler) {
		handler.onMessageReceived(this);
	}

	public Demo getDemo() {
		return demo;
	}
	
	

}
