package org.dmp.gwtpurdy.client.dialog;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;

public class ModalDialogPresenter implements ModalDialogEventHandler {
    
    public interface Display {
        void showDisplay();
        void hideDisplay();
        void configure(DialogConfig dialogConfig);
    }

    private Map<String, Display> dialogs = new HashMap<String, Display>();
    
    @SuppressWarnings("unused")
    private EventBus eventBus;
    
    public ModalDialogPresenter(EventBus eventBus) {
        this.eventBus = eventBus;
        
        InformationDialog infoDialog = new InformationDialog();
        this.addDialog(BaseDialogId.INFORMATION_DIALOG.name(), infoDialog);
        
        PleaseWaitDialog pleaseWaitDialog = new PleaseWaitDialog();
        this.addDialog(BaseDialogId.SPINNER.name(), pleaseWaitDialog);
        
        ErrorDialog errorDialog = new ErrorDialog();
        this.addDialog(BaseDialogId.ERROR_DIALOG.name(), errorDialog);
    }

    @Override
    public void onMessageReceived(ModalDialogEvent event) {
        Display dialog = dialogs.get(event.getDialogId());
        if (dialog!=null) {
            if (event.isShow()) {
                if (event.getDialogConfig()!=null) {
                    dialog.configure(event.getDialogConfig());
                }
                dialog.showDisplay();
            } else {
                dialog.hideDisplay();
            }
        } else {
            GWT.log("No modal dialog configured with event ID of " + event.getDialogId());
        }
    }
    
    public void addDialog(String displayId, Display dialog) {
        dialogs.put(displayId, dialog);
        dialog.showDisplay();
        dialog.hideDisplay();
    }
    
}
