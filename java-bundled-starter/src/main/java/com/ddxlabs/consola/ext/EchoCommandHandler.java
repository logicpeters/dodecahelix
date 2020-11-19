package com.ddxlabs.consola.ext;

import com.ddxlabs.consola.ExitHandler;
import com.ddxlabs.consola.command.BaseCommandHandler;
import com.ddxlabs.consola.command.WordPromptHandler;
import com.ddxlabs.consola.prefs.Constants;
import com.ddxlabs.consola.response.Response;
import com.ddxlabs.consola.response.StyledResponse;
import com.ddxlabs.consola.response.StyledResponseBuilder;
import com.ddxlabs.consola.response.TextStyle;
import com.ddxlabs.consola.view.ViewContainer;

public class EchoCommandHandler extends BaseCommandHandler {

    public EchoCommandHandler(ViewContainer app, WordPromptHandler wordPromptHandler, ExitHandler exitHandler) {
        super(app, wordPromptHandler, exitHandler);
    }

    protected Response respondToCommand(String subject, String command) {
        String commandEcho = "> " + command;
        if (!Constants.DEFAULT_SUBJECT.equalsIgnoreCase(subject)) {
            commandEcho = String.format("(%s)> %s", subject, command);
        }

        StyledResponse response = new StyledResponseBuilder()
                .line(commandEcho, TextStyle.SYSTEM)
                .line("response not implemented")
                .newline()
                .build();

        return response;
    }
}
