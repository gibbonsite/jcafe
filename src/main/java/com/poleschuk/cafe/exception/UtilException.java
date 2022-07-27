package com.poleschuk.cafe.exception;

/**
 * UtilException class is exception class for util classes.
 */
public class UtilException extends Exception {
    public UtilException() {
        super();
    }
    
    public UtilException(String message) {
        super(message);
    }

    public UtilException(Exception cause) {
        super(cause);
    }

    public UtilException(String message, Exception cause) {
        super(message, cause);
    }
}
