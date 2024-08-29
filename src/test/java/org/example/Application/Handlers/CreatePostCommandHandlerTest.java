package org.example.Application.Handlers;

import org.example.Application.Commands.CreatePostCommand;
import org.example.Application.DTOs.CreatePostDTO;
import org.example.Application.Exceptions.Impls.NullCommandException;
import org.example.Application.Services.Interfaces.PostApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

class CreatePostCommandHandlerTest {

    @Mock
    private PostApplicationService postApplicationService;

    @InjectMocks
    private CreatePostCommandHandler createPostCommandHandler;

    private CreatePostCommand createPostCommand;
    private CreatePostDTO createPostDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        createPostDTO = new CreatePostDTO.Builder()
                .content("content")
                .userUsername("cris6h16")
                .build();

        createPostCommand = new CreatePostCommand(createPostDTO);
    }

    @Test
    public void handle() {
        createPostCommandHandler.handle(createPostCommand);
        verify(postApplicationService).create(createPostDTO);
    }

    @Test
    public void handleWithNullCommand() {
        assertThrows(NullCommandException.class, () -> createPostCommandHandler.handle(null));
    }
}

