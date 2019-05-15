package org.ddx.ds;

/**
 * Exception for handling case when trying to access a stack that is empty.
 *
 */
public class UnderflowError extends Exception {

    public UnderflowError() {
    }

    public UnderflowError(String s) {
        super(s);
    }

}
