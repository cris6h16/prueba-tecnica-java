package org.example.Application.Handlers;

import org.example.Application.Commands.FindByUsernameFollowingEagerCommand;
import org.example.Application.DTOs.UserDTO;
import org.example.Application.Exceptions.Impls.NullCommandException;
import org.example.Application.Services.Interfaces.UserApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FindByUsernameFollowingEagerCommandHandlerTest {

    @Mock
    private UserApplicationService userApplicationService;

    @InjectMocks
    private FindByUsernameFollowingEagerCommandHandler findByUsernameFollowingEagerCommandHandler;

    private FindByUsernameFollowingEagerCommand findByUsernameFollowingEagerCommand;
    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userDTO = new UserDTO.Builder()
                .setId("1")
                .setUsername("cris6h16")
                .setFollowing(new HashSet<>())
                .setPosts(new HashSet<>())
                .build();

        findByUsernameFollowingEagerCommand = new FindByUsernameFollowingEagerCommand("cris6h16");
    }

    @Test
    public void handle() {
        when(userApplicationService.findByUsernameFollowingEager("cris6h16")).thenReturn(userDTO);

        UserDTO result = findByUsernameFollowingEagerCommandHandler.handle(findByUsernameFollowingEagerCommand);

        verify(userApplicationService).findByUsernameFollowingEager("cris6h16");
        assertEquals(userDTO, result);
    }

    @Test
    public void handleWithNullCommand() {
        assertThrows(NullCommandException.class, () -> findByUsernameFollowingEagerCommandHandler.handle(null));
    }
}