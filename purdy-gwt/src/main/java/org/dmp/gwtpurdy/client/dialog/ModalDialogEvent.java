package org.dmp.gwtpurdy.client.dialog;

import com.google.gwt.event.shared.GwtEvent;

public class ModalDialogEvent extends GwtEvent<ModalDialogEventHandler> {
    
    public static Type<ModalDialogEventHandler> TYPE = new Type<ModalDialogEventHandler>();

    private String dialogId;
    private boolean show;
    
    private String dialogTitle;
    private String dialogMessage;
    private DialogConfig dialogConfig;
    
    /**
     * 
     * GwtEvent to show or hide a pre-configured modal dialog
     * 
     * @param dialogId - unique identifier for the dialog to be shown
     * @param show - true for showing the dialog, false to hide
     */
    public ModalDialogEvent(String dialogId, boolean show) {
        this.dialogId = dialogId;
        this.show = show;
    }
    
    public ModalDialogEvent(String dialogId, DialogConfig config) {
        this(dialogId, true);
        this.dialogConfig = config;
    }

    @Override
    public Type<ModalDialogEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ModalDialogEventHandler handler) {
        handler.onMessageReceived(this);
    }

    public String getDialogId() {
        return dialogId;
    }

    public boolean isShow() {
        return show;
    }

    public String getDialogTitle() {
        return dialogTitle;
    }

    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }

    public String getDialogMessage() {
        return dialogMessage;
    }

    public void setDialogMessage(String dialogMessage) {
        this.dialogMessage = dialogMessage;
    }

    public DialogConfig getDialogConfig() {
        return dialogConfig;
    }

    public void setDialogConfig(DialogConfig dialogConfig) {
        this.dialogConfig = dialogConfig;
    }   

}
