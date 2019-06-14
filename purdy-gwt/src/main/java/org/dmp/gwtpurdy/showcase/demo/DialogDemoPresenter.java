package org.dmp.gwtpurdy.showcase.demo;

import org.dmp.gwtpurdy.client.dialog.BaseDialogId;
import org.dmp.gwtpurdy.client.dialog.DialogConfig;
import org.dmp.gwtpurdy.client.dialog.ModalDialogEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;

public class DialogDemoPresenter {
	
	public interface Display {
		HasClickHandlers getInfoButton();
		HasClickHandlers getPleaseWaitButton();
		HasClickHandlers getErrorDialogButton();
		HasClickHandlers getFormDialogButton();
	}
	
	private Display display;
	private EventBus eventBus;
	
	public DialogDemoPresenter(Display display, EventBus eventBus) {
		this.display = display;
		this.eventBus = eventBus;

		bind();
	}

	private void bind() {
		display.getInfoButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				DialogConfig dialogConfig = new DialogConfig("Information", "This is an informational dialog.");
                dialogConfig.setDialogHeight("140px");
				eventBus.fireEvent(new ModalDialogEvent(BaseDialogId.INFORMATION_DIALOG.name(), dialogConfig));
			}
		});
		
		display.getPleaseWaitButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				DialogConfig dialogConfig = new DialogConfig("Please Wait", "Please Wait Five Seconds..");
				eventBus.fireEvent(new ModalDialogEvent(BaseDialogId.SPINNER.name(), dialogConfig));

				Timer timer = new Timer() {
					@Override
					public void run() {
						// hide the spinner
						eventBus.fireEvent(new ModalDialogEvent(BaseDialogId.SPINNER.name(), false));
					}
				};
				timer.schedule(5000);
			}
		});
		
		display.getErrorDialogButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
			    DialogConfig dialogConfig = new DialogConfig("Error", "This is a generic error dialog.");
			    dialogConfig.setDialogHeight("140px");
                eventBus.fireEvent(new ModalDialogEvent(BaseDialogId.ERROR_DIALOG.name(), dialogConfig));
			}
		});
		
		display.getFormDialogButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
	            eventBus.fireEvent(new ModalDialogEvent(LoginFormDialog.DIALOG_ID, true));
			}
		});
		
	}

}
