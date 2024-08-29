package org.example.Application.Handlers;

import org.example.Application.Commands.FindByUsernameCommand;
import org.example.Application.DTOs.UserDTO;
import org.example.Application.Services.Interfaces.UserApplicationService;

public class FindByUsernameCommandHandler {
    private final UserApplicationService userApplicationService;

    public FindByUsernameCommandHandler(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    public UserDTO handle(FindByUsernameCommand command) {
        return userApplicationService.findByUsername(command.getUsername());
    }
}
