package com.ddxlabs.consola.system;

import com.ddxlabs.consola.CommandHandler;
import com.ddxlabs.consola.Response;
import com.ddxlabs.consola.StyledLine;

/**
 * Created on 6/2/2019.
 */
public class SystemCommandHandler implements CommandHandler {

    @Override
    public Response processCommand(String command) {
        SystemResponse response = new SystemResponse();

        response.addResponseLine(new StyledLine("Processing command " + command));
        response.addBlankLine();
        return response;
    }
}
