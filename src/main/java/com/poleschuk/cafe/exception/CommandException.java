package com.poleschuk.cafe.exception;

/**
 * CommandException class is exception class for commands.
 */
public class CommandException extends Exception {
    public CommandException() {
        super();
    }
    
    public CommandException(String message) {
        super(message);
    }

    public CommandException(Exception cause) {
        super(cause);
    }

    public CommandException(String message, Exception cause) {
        super(message, cause);
    }

}
