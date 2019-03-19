package org.courses.commands;

public interface Command {
    void parse(String[] args);

    void execute();
}
