package com.poleschuk.cafe.exception;

/**
 * ServiceException class is exception class for services.
 */
public class ServiceException extends Exception {
    public ServiceException() {
        super();
    }
    
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Exception cause) {
        super(cause);
    }

    public ServiceException(String message, Exception cause) {
        super(message, cause);
    }

}
