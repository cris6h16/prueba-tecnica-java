package org.example.Application.Handlers;

import org.example.Application.Commands.FindByUsernameFollowingEagerCommand;
import org.example.Application.DTOs.UserDTO;
import org.example.Application.Services.Interfaces.UserApplicationService;

public class FindByUsernameFollowingEagerCommandHandler {
    private final UserApplicationService userApplicationService;

    public FindByUsernameFollowingEagerCommandHandler(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    public UserDTO handle(FindByUsernameFollowingEagerCommand command) {
        return userApplicationService.findByUsernameFollowingEager(
                command.getUsername()
        );
    }
}
