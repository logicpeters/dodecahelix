package org.dmp.gwtpurdy.showcase.demo;


import org.dmp.gwtpurdy.client.dialog.BaseDialogId;
import org.dmp.gwtpurdy.client.dialog.DialogConfig;
import org.dmp.gwtpurdy.client.dialog.ModalDialogEvent;
import org.dmp.gwtpurdy.client.utils.GwtStringUtils;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;

/**
 *   Handles interactions and business logic for the DefaultForm (i.e; submit action)
 *   
 * @author dpeters
 *
 */
public class DefaultFormDemoPresenter {
	
	public interface Display {
		HasClickHandlers getSubmitButton();
		String getNameValue();
        String getDescriptionValue();
        String getGenderValue();
		void invalidateName(String message);
        void invalidateDescription(String string);
        void invalidateGender(String string);
		void clearValidationErrors();
	}

	private Display display;
	private EventBus eventBus;
	
	public DefaultFormDemoPresenter(Display display, EventBus eventBus) {
		this.display = display;
		this.eventBus = eventBus;

		bind();
	}

	private void bind() {
		display.getSubmitButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
			    if (validateForm()) {			
			    	DialogConfig dialogConfig = new DialogConfig("Form Saved", "Your information has been submitted.  Thank you.");
			    	dialogConfig.setDialogHeight("150px");
					ModalDialogEvent dialogEvent = new ModalDialogEvent(BaseDialogId.INFORMATION_DIALOG.name(), 
					        dialogConfig);
					eventBus.fireEvent(dialogEvent);
				}
			}
		});
	}
	
	private boolean validateForm() {
	    boolean valid = true;
	    
	    display.clearValidationErrors();
	    
	    String name = display.getNameValue();
        String description = display.getDescriptionValue();
        String gender = display.getGenderValue();
        
	    if (GwtStringUtils.isBlank(name)
	            || !name.contains(",")) {
            display.invalidateName(null);
            valid = false;
        }
	    
	    if (!GwtStringUtils.isBlank(description)
	            && description.length()>100) {
	        display.invalidateDescription("Longer than 100 words!");
	        valid = false;
	    }
	    
	    if (GwtStringUtils.isBlank(gender)) {
	        display.invalidateGender("You must select a gender!");
	        valid = false;
	    }
	    
        return valid;
	}


}
