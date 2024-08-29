package org.example.Application.Handlers;

import org.example.Application.Commands.FollowUserCommand;
import org.example.Application.Services.Interfaces.UserApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class FollowUserCommandHandlerTest {

    @Mock
    private UserApplicationService userApplicationService;

    @InjectMocks
    private FollowUserCommandHandler followUserCommandHandler;

    private FollowUserCommand followUserCommand;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        followUserCommand = new FollowUserCommand("cris6h16", "cristianHerrera");
    }

    @Test
    public void handle() {
        followUserCommandHandler.handle(followUserCommand);
        verify(userApplicationService).follow("cris6h16", "cristianHerrera");
    }

}