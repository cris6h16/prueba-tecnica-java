package org.example.Infrastructure.Adapter.Input.REST;

import org.example.Application.Commands.CreatePostCommand;
import org.example.Application.Exceptions.Impls.UserNotFoundException;
import org.example.Application.Exceptions.Impls.UsernameIsNullOrBlankException;
import org.example.Application.Handlers.CreatePostCommandHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreatePostCommandHandler createPostCommandHandler;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePostSuccess() throws Exception {
        // Arrange
        String username = "cris6h16";
        String content = "Test post content";

        // Act & Assert
        mockMvc.perform(post("/api/v1/posts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"" + username + "\", \"content\": \"" + content + "\"}"))
                .andExpect(status().isOk());

        verify(createPostCommandHandler, times(1)).handle(any(CreatePostCommand.class));
    }

    @Test
    void create_UserNotFoundException() throws Exception {
        // Arrange
        doThrow(new UserNotFoundException("cris6h16")).when(createPostCommandHandler).handle(any(CreatePostCommand.class));

        // Act & Assert
        mockMvc.perform(post("/api/v1/posts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("No se encontró ningún usuario @cris6h16")
                );

        verify(createPostCommandHandler, times(1)).handle(any(CreatePostCommand.class));
    }
    //UsernameIsNullOrBlankException

    @Test
    void create_UsernameIsNullOrBlankException() throws Exception {
        // Arrange
        doThrow(new UsernameIsNullOrBlankException()).when(createPostCommandHandler).handle(any(CreatePostCommand.class));

        // Act & Assert
        mockMvc.perform(post("/api/v1/posts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value("username no puede ser null o vacio")
                );
    }
}
