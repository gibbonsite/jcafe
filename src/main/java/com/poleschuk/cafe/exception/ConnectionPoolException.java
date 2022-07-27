package com.poleschuk.cafe.exception;

/**
 * ConnectionPoolException class is exception class for connection pool errors.
 */
public class ConnectionPoolException extends Exception{
    public ConnectionPoolException() {
        super();
    }
    
    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(Exception cause) {
        super(cause);
    }

    public ConnectionPoolException(String message, Exception cause) {
        super(message, cause);
    }
}