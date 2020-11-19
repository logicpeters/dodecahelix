package com.ddxlabs.consola.command;

import com.ddxlabs.consola.Application;
import com.ddxlabs.consola.ExitHandler;
import com.ddxlabs.consola.command.CommandHandler;
import com.ddxlabs.consola.command.WordPromptHandler;
import com.ddxlabs.consola.prefs.Constants;
import com.ddxlabs.consola.response.Response;
import com.ddxlabs.consola.response.StyledLine;
import com.ddxlabs.consola.response.StyledResponse;
import com.ddxlabs.consola.response.StyledResponseBuilder;
import com.ddxlabs.consola.view.ViewContainer;

import javax.swing.text.BadLocationException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A base command handler that simply echoes the command sent.
 */
public abstract class BaseCommandHandler implements CommandHandler {

    private ViewContainer app;
    private WordPromptHandler wordPromptHandler;
    private ExitHandler exitHandler;

    public BaseCommandHandler(ViewContainer app, WordPromptHandler wordPromptHandler, ExitHandler exitHandler) {
        this.app = app;
        this.exitHandler = exitHandler;
        this.wordPromptHandler = wordPromptHandler;
    }

    @Override
    public void processCommand(String subject, String command) {
        if ("exit".equalsIgnoreCase(command)) {
            exitHandler.exit();
        }

        Response response = respondToCommand(subject, command);
        for (StyledLine responseLine : response.getOutputDisplay()) {
            try {
                app.getConsole().addLine(responseLine);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }

        if (response.getSubjects().isEmpty()) {
            app.getCommandInput().updateSubjects(response.getSubjects());
        }

        // add available words to WordPromtHandler
        Set<String> commands = response.getResources().stream()
                .flatMap(res -> res.getCommands().stream())
                .collect(Collectors.toSet());
        commands.addAll(Arrays.asList(Constants.DEFAULT_COMMAND_WORDS));
        wordPromptHandler.setAvailableWords(commands);
    }

    protected abstract Response respondToCommand(String subject, String command);
}
