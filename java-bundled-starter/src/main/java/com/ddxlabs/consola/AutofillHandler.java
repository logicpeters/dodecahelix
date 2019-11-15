package com.ddxlabs.consola;

import com.ddxlabs.consola.view.ViewContainer;

/**
 *  Automatically add the next word of the command if there is only one option
 */
public class AutofillHandler {

    private ViewContainer app;
    private WordPromptHandler promptHandler;

    public AutofillHandler(ViewContainer app, WordPromptHandler promptHandler) {
        this.app = app;
        this.promptHandler = promptHandler;
    }

    public void autofill() {
        promptHandler.onlyWord().ifPresent(word -> app.getCommandInput().autofill(word));
    }

}
