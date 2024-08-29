package org.example.Infrastructure.Adapter.Input;

import org.example.Application.Handlers.CreatePostCommandHandler;
import org.example.Application.Handlers.FindByUsernameCommandHandler;
import org.example.Application.Handlers.FindByUsernameFollowingEagerCommandHandler;
import org.example.Application.Handlers.FollowUserCommandHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsoleApiTest {

    private FollowUserCommandHandler followUserCommandHandler;
    private CreatePostCommandHandler createPostCommandHandler;
    private FindByUsernameCommandHandler findByUsernameCommandHandler;
    private FindByUsernameFollowingEagerCommandHandler findByUsernameFollowingEagerCommandHandler;
    private ConsoleApi consoleApi;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        followUserCommandHandler = mock(FollowUserCommandHandler.class);
        createPostCommandHandler = mock(CreatePostCommandHandler.class);
        findByUsernameCommandHandler = mock(FindByUsernameCommandHandler.class);
        findByUsernameFollowingEagerCommandHandler = mock(FindByUsernameFollowingEagerCommandHandler.class);
        consoleApi = new ConsoleApi(followUserCommandHandler, createPostCommandHandler, findByUsernameCommandHandler, findByUsernameFollowingEagerCommandHandler);

        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void runHandlesPostCommand() {
        String input = "post @user1 Hello World\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        consoleApi.run();

        verify(createPostCommandHandler).handle(any(CreatePostCommand.class));
    }

    @Test
    void runHandlesFollowCommand() {
        String input = "follow @user1 @user2\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        consoleApi.run();

        verify(followUserCommandHandler).handle(any(FollowUserCommand.class));
    }

    @Test
    void runHandlesDashboardCommand() {
        String input = "dashboard @user1\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        UserDTO user = mock(UserDTO.class);
        when(findByUsernameFollowingEagerCommandHandler.handle(any(FindByUsernameFollowingEagerCommand.class))).thenReturn(user);
        when(user.getFollowing()).thenReturn(new HashSet<>());

        consoleApi.run();

        verify(findByUsernameFollowingEagerCommandHandler).handle(any(FindByUsernameFollowingEagerCommand.class));
    }

    @Test
    void runHandlesWallCommand() {
        String input = "wall @user1\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        UserDTO user = mock(UserDTO.class);
        when(findByUsernameCommandHandler.handle(any(FindByUsernameCommand.class))).thenReturn(user);
        when(user.getPosts()).thenReturn(new HashSet<>());

        consoleApi.run();

        verify(findByUsernameCommandHandler).handle(any(FindByUsernameCommand.class));
    }

    @Test
    void runHandlesInvalidCommandGracefully() {
        String input = "invalidCommand\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        consoleApi.run();

        assertTrue(outputStream.toString().contains("Essribe 'exit' para salir... Ya puedes empezar a escribir comandos"));
    }

    @Test
    void wallPrintsPostsCorrectly() {
        UserDTO user = mock(UserDTO.class);
        when(findByUsernameCommandHandler.handle(any(FindByUsernameCommand.class))).thenReturn(user);

        PostDTO post = mock(PostDTO.class);
        when(post.getContent()).thenReturn("Hello World");
        when(post.getUserUsername()).thenReturn("user1");
        when(post.getInstant()).thenReturn(123456789L);

        when(user.getPosts()).thenReturn(new HashSet<>(Collections.singletonList(post)));

        consoleApi.wall("wall @user1");

        assertTrue(outputStream.toString().contains("\"Hello World\" @user1"));
    }

    @Test
    void dashboardPrintsFollowingPostsCorrectly() {
        UserDTO user = mock(UserDTO.class);
        UserDTO followingUser = mock(UserDTO.class);
        PostDTO post = mock(PostDTO.class);

        when(findByUsernameFollowingEagerCommandHandler.handle(any(FindByUsernameFollowingEagerCommand.class))).thenReturn(user);
        when(user.getFollowing()).thenReturn(new HashSet<>(Collections.singletonList(followingUser)));

        when(followingUser.getPosts()).thenReturn(new HashSet<>(Collections.singletonList(post)));
        when(post.getContent()).thenReturn("Hello from following");
        when(post.getUserUsername()).thenReturn("user2");
        when(post.getInstant()).thenReturn(123456789L);

        consoleApi.dashboard("dashboard @user1");

        assertTrue(outputStream.toString().contains("\"Hello from following\" @user2"));
    }

    @Test
    void followExecutesCorrectly() {
        consoleApi.follow("follow @user1 @user2");

        verify(followUserCommandHandler).handle(any(FollowUserCommand.class));
    }

    @Test
    void postExecutesCorrectly() {
        consoleApi.post("post @user1 Hello World");

        verify(createPostCommandHandler).handle(any(CreatePostCommand.class));
    }
}
