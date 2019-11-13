package com.ddxlabs.consola;

public class ConsolaException extends RuntimeException {

    public ConsolaException() {
    }

    public ConsolaException(String message) {
        super(message);
    }

    public ConsolaException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConsolaException(Throwable cause) {
        super(cause);
    }
}
