package com.ddxlabs.consola.view;

import java.util.ArrayList;
import java.util.List;

public interface ViewContainer {

    public Console getConsole();

    public CommandInput getCommandInput();

    public WordPromptRow getWordPromptRow();

    public Menu getMenu();

    public List<ViewComponent> allViews();

}
