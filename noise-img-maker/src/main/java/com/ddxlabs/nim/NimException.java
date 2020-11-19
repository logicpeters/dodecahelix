package com.ddxlabs.nim;

public class NimException extends RuntimeException {

    public NimException() {
    }

    public NimException(String message) {
        super(message);
    }

    public NimException(String message, Throwable cause) {
        super(message, cause);
    }

    public NimException(Throwable cause) {
        super(cause);
    }
}
