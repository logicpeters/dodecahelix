package org.dmp.gwtpurdy.showcase;

import org.dmp.gwtpurdy.showcase.demo.Demo;
import org.dmp.gwtpurdy.showcase.demo.DisplayDemoEvent;
import org.dmp.gwtpurdy.showcase.demo.DisplayDemoEventHandler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;

public class ShowcasePresenter implements DisplayDemoEventHandler {

	public interface Display {
		HasClickHandlers getDemoList();
		String getDemoListValue();
		void showDemo(Demo demo);
	}

	private Display display;
	private EventBus eventBus;

	public ShowcasePresenter(Display display, EventBus eventBus) {
		this.display = display;
		this.eventBus = eventBus;

		bind();
	}

	private void bind() {
		display.getDemoList().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String comboboxSelectionValue = display.getDemoListValue();
				Demo requestedDemo = Demo.valueOf(comboboxSelectionValue);
				
				// yes, this is lame -- sending an event to itself.  just here for MVP demonstration
				eventBus.fireEvent(new DisplayDemoEvent(requestedDemo));
			}

		});

	}

	@Override
	public void onMessageReceived(DisplayDemoEvent event) {
		display.showDemo(event.getDemo());		
	}

}
