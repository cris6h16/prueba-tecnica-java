package org.example.Application.Handlers;

import org.example.Application.Commands.FindByUsernameCommand;
import org.example.Application.DTOs.UserDTO;
import org.example.Application.Services.Interfaces.UserApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FindByUsernameCommandHandlerTest {

    @Mock
    private UserApplicationService userApplicationService;

    @InjectMocks
    private FindByUsernameCommandHandler findByUsernameCommandHandler;

    private FindByUsernameCommand findByUsernameCommand;
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

        findByUsernameCommand = new FindByUsernameCommand("cris6h16");
    }

    @Test
    public void handle() {
        when(userApplicationService.findByUsername("cris6h16")).thenReturn(userDTO);
        UserDTO result = findByUsernameCommandHandler.handle(findByUsernameCommand);
        verify(userApplicationService).findByUsername("cris6h16");
        assertEquals(userDTO, result);
    }

}