package com.nexmo.cli;

public class CommandFailedException extends Throwable {
    public CommandFailedException(Exception e) {
        super(e);
    }

    public CommandFailedException(String errorMessage, Exception e) {
        super(errorMessage, e);
    }

    public CommandFailedException(String errorMessage) {
        super(errorMessage);
    }
}
