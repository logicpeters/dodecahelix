package org.dmp.gwtpurdy.client.dialog;

import com.google.gwt.event.shared.EventHandler;

public interface ModalDialogEventHandler extends EventHandler {

    void onMessageReceived(ModalDialogEvent event);
    
}
