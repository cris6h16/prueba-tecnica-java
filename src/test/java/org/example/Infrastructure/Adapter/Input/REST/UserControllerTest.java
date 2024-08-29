package org.example.Infrastructure.Adapter.Input.REST;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Application.Commands.FindByUsernameCommand;
import org.example.Application.Commands.FindByUsernameFollowingEagerCommand;
import org.example.Application.Commands.FollowUserCommand;
import org.example.Application.DTOs.PostDTO;
import org.example.Application.DTOs.UserDTO;
import org.example.Application.Exceptions.Impls.AlreadyFollowingException;
import org.example.Application.Exceptions.Impls.UserNotFoundException;
import org.example.Application.Handlers.FindByUsernameCommandHandler;
import org.example.Application.Handlers.FindByUsernameFollowingEagerCommandHandler;
import org.example.Application.Handlers.FollowUserCommandHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FollowUserCommandHandler followUserCommandHandler;

    @Mock
    private FindByUsernameCommandHandler findByUsernameCommandHandler;

    @Mock
    private FindByUsernameFollowingEagerCommandHandler findByUsernameFollowingEagerCommandHandler;

    @InjectMocks
    private UserController userController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void FollowSuccess() throws Exception {
        // Arrange
        String followerUsername = "cris6h16";
        String followedUsername = "cristianHerrera";

        // Act & Assert
        mockMvc.perform(post("/api/v1/users/follow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(followerUsername)))
                .andExpect(status().isOk());

        verify(followUserCommandHandler, times(1)).handle(any(FollowUserCommand.class));
    }

    @Test
    void FollowUserNotFound() throws Exception {
        // Arrange
        String followerUsername = "cris6h16";
        String followedUsername = "helloWorld";
        doThrow(new UserNotFoundException(followerUsername)).when(followUserCommandHandler).handle(any(FollowUserCommand.class));

        // Act & Assert
        mockMvc.perform(post("/api/v1/users/follow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"followerUsername\": \"" + followerUsername + "\", \"followedUsername\": \"" + followedUsername + "\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("No se encontró ningún usuario @" + followerUsername));

        verify(followUserCommandHandler, times(1)).handle(any(FollowUserCommand.class));
    }

    @Test
    void FollowAlreadyFollowing() throws Exception {
        // Arrange
        String followerUsername = "cris6h16";
        String followedUsername = "cristianHerrera";
        doThrow(new AlreadyFollowingException(followerUsername, followedUsername))
                .when(followUserCommandHandler).handle(any(FollowUserCommand.class));

        // Act & Assert
        mockMvc.perform(post("/api/v1/users/follow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(followerUsername)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.message").value(followerUsername + " ya está siguiendo a @" + followedUsername));

        verify(followUserCommandHandler, times(1)).handle(any(FollowUserCommand.class));
    }

    @Test
    void DashboardSuccess() throws Exception {
        // Arrange
        String username = "cris6h16";
        UserDTO userDTO = new UserDTO.Builder()
                .setUsername(username)
                .setPosts(new HashSet<>())
                .setFollowing(createUsersWithPosts())
                .build();
        when(findByUsernameFollowingEagerCommandHandler.handle(any(FindByUsernameFollowingEagerCommand.class)))
                .thenReturn(userDTO);

        Set<PostDTO> posts = userDTO.getFollowing().stream()
                .flatMap(user -> user.getPosts().stream())
                .collect(Collectors.toSet());

        // Act & Assert
        mockMvc.perform(get("/api/v1/users/dashboard")
                        .param("username", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].content").value("Post 1"))
                .andExpect(jsonPath("$.[1].content").value("Post 2"));

        verify(findByUsernameFollowingEagerCommandHandler, times(1)).handle(any(FindByUsernameFollowingEagerCommand.class));
    }

    private Set<UserDTO> createUsersWithPosts() {
        UserDTO user1 = new UserDTO.Builder()
                .setUsername("prueba")
                .setPosts(Collections.singleton(new PostDTO.Builder().setContent("Post 1").build()))
                .build();
        UserDTO user2 = new UserDTO.Builder()
                .setUsername("tecnica")
                .setPosts(Collections.singleton(new PostDTO.Builder().setContent("Post 2").build()))
                .build();
        return new HashSet<>(Set.of(user1, user2));
    }

    @Test
    void DashboardUserNotFound() throws Exception {
        // Arrange
        String username = "helloWorld";
        doThrow(new UserNotFoundException(username)).when(findByUsernameFollowingEagerCommandHandler).handle(any(FindByUsernameFollowingEagerCommand.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/users/dashboard")
                        .param("username", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("No se encontró ningún usuario @" + username));

        verify(findByUsernameFollowingEagerCommandHandler, times(1)).handle(any(FindByUsernameFollowingEagerCommand.class));
    }

    @Test
    void WallSuccess() throws Exception {
        // Arrange
        String username = "cris6h16";
        UserDTO userDTO = new UserDTO.Builder()
                .setUsername(username)
                .setPosts(Collections.singleton(new PostDTO.Builder().setContent("Content 123").build()))
                .build();
        when(findByUsernameCommandHandler.handle(any(FindByUsernameCommand.class)))
                .thenReturn(userDTO);

        // Act & Assert
        mockMvc.perform(get("/api/v1/users/wall")
                        .param("username", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].content").value("Content 123"));

        verify(findByUsernameCommandHandler, times(1)).handle(any(FindByUsernameCommand.class));
    }

    @Test
    void WallUserNotFound() throws Exception {
        // Arrange
        String username = "hellwoWorld";
        doThrow(new UserNotFoundException(username)).when(findByUsernameCommandHandler).handle(any(FindByUsernameCommand.class));

        // Act & Assert
        mockMvc.perform(get("/api/v1/users/wall")
                        .param("username", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("No se encontró ningún usuario @" + username));

        verify(findByUsernameCommandHandler, times(1)).handle(any(FindByUsernameCommand.class));
    }
}
