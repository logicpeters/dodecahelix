package org.dmp.gwtpurdy.showcase.demo;

import com.google.gwt.event.shared.GwtEvent;

public class FormSubmitEvent extends GwtEvent<FormSubmitEventHandler> {

	public static Type<FormSubmitEventHandler> TYPE = new Type<FormSubmitEventHandler>();

	public FormSubmitEvent() {
	}

	@Override
	public Type<FormSubmitEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(FormSubmitEventHandler handler) {
		handler.onMessageReceived(this);
	}


}
