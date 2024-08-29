package org.example.Application.Commands;

public class FindByUsernameFollowingEagerCommand {
    private final String username;

    public FindByUsernameFollowingEagerCommand(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
