package com.ddxlabs.consola;

import java.awt.*;

public class ExitHandler {

    private Application app;

    public ExitHandler(Application app) {
        this.app = app;
    }

    public void exit() {
        app.disposeUI();

        // TODO - handle context closure
    }

}
