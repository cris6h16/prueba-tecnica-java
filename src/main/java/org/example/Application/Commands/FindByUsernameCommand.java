package org.example.Application.Commands;

public class FindByUsernameCommand {
    private final String username;

    public FindByUsernameCommand(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
