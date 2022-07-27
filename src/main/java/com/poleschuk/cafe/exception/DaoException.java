package com.poleschuk.cafe.exception;

/**
 * DaoException class is exception class for Dao errors.
 */
public class DaoException extends Exception {
    public DaoException() {
        super();
    }
    
    public DaoException(String message) {
        super(message);
    }

    public DaoException(Exception cause) {
        super(cause);
    }

    public DaoException(String message, Exception cause) {
        super(message, cause);
    }

}
