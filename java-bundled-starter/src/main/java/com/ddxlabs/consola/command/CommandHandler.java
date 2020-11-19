package com.ddxlabs.consola.command;

import com.ddxlabs.consola.response.Response;

/**
 * Created on 6/2/2019.
 */
public interface CommandHandler {

    public void processCommand(String subject, String command);

}
