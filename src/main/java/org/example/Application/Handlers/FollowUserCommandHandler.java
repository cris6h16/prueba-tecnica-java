package org.example.Application.Handlers;

import org.example.Application.Commands.FollowUserCommand;
import org.example.Application.Services.Interfaces.UserApplicationService;

public class FollowUserCommandHandler {
    private final UserApplicationService userApplicationService;

    public FollowUserCommandHandler(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    public void handle(FollowUserCommand command) {
        userApplicationService.follow(
                command.getFollowerUsername(),
                command.getFollowedUsername()
        );
    }
}
