package com.ddxlabs.consola.system;

import com.ddxlabs.consola.Application;
import com.ddxlabs.consola.CommandHandler;
import com.ddxlabs.consola.response.Response;
import com.ddxlabs.consola.response.StyledLine;
import com.ddxlabs.consola.response.StyledResponse;

import javax.swing.text.BadLocationException;

/**
 * A base command handler that simply echoes the command sent.
 */
public class EchoCommandHandler implements CommandHandler {

    private Application app;

    public EchoCommandHandler(Application app) {
        this.app = app;
    }

    @Override
    public void processCommand(String subject, String command) {
        if ("exit".equalsIgnoreCase(command)) {
            app.exitApp();
        }

        Response response = respondToCommand(subject, command);
        for (StyledLine responseLine : response.getOutputDisplay()) {
            try {
                app.getConsole().addLine(responseLine);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }


    private Response respondToCommand(String subject, String command) {
        StyledResponse response = new StyledResponse();

        response.addResponseLine(new StyledLine(subject + ", " + command));
        response.addBlankLine();
        return response;
    }
}
