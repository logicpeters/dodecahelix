package org.dmp.gwtpurdy.client.dialog;

public class DialogConfig {

    private String dialogTitle;
    private String dialogMessage;
    private String buttonText;
    
    private String dialogHeight;
    private String dialogWidth;
    
    public DialogConfig() {
        
    }
    
    public DialogConfig(String title) {
        this.dialogTitle = title;
    }
    
    public DialogConfig(String title, String message) {
        this.dialogTitle = title;
        this.dialogMessage = message;
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
    public String getButtonText() {
        return buttonText;
    }
    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getDialogHeight() {
        return dialogHeight;
    }

    public void setDialogHeight(String dialogHeight) {
        this.dialogHeight = dialogHeight;
    }

    public String getDialogWidth() {
        return dialogWidth;
    }

    public void setDialogWidth(String dialogWidth) {
        this.dialogWidth = dialogWidth;
    }
    
    
    
}
