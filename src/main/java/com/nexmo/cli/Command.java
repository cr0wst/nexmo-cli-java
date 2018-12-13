package com.nexmo.cli;

public interface Command {
    void execute() throws CommandFailedException;
}
