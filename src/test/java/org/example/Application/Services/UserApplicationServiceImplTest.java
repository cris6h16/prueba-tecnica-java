package org.example.Application.Services;

import org.example.Application.DTOs.UserDTO;
import org.example.Application.Exceptions.Impls.AlreadyFollowingException;
import org.example.Application.Exceptions.Impls.UserNotFoundException;
import org.example.Domain.Model.UserModel;
import org.example.Domain.Repositories.UserDomainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserApplicationServiceImplTest {


    @Mock
    private UserDomainRepository userRepository;

    @InjectMocks
    private UserApplicationServiceImpl userService;

    private UserModel userModel;
    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userModel =  new UserModel.Builder()
                .setId("1")
                .setUsername("cris6h16")
                .setPosts(new HashSet<>())
                .setFollowing(new HashSet<>())
                .build();

        userDTO = new UserDTO.Builder()
                .setId(userModel.getId())
                .setUsername(userModel.getUsername())
                .setFollowing(new HashSet<>())
                .setPosts(new HashSet<>())
                .build();
    }

    @Test
    public void Follow_Success() {
        when(userRepository.findByUsername("follower")).thenReturn(Optional.of(userModel));
        when(userRepository.findByUsername("followed")).thenReturn(Optional.of(userModel));

        userService.follow("follower", "followed");

        verify(userRepository).follow("follower", "followed");
    }

    @Test
    public void Follow_UserNotFound() {
        when(userRepository.findByUsername("follower")).thenReturn(Optional.of(userModel));
        when(userRepository.findByUsername("followed")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.follow("follower", "followed"));
    }

    @Test
    public void Follow_AlreadyFollowing() {
        UserModel followed =  new UserModel.Builder()
                .setId("2")
                .setUsername("followed")
                .setPosts(new HashSet<>())
                .setFollowing(new HashSet<>())
                .build();
        userModel.getFollowing().add(followed);

        when(userRepository.findByUsername("follower")).thenReturn(Optional.of(userModel));
        when(userRepository.findByUsername("followed")).thenReturn(Optional.of(followed));

        assertThrows(AlreadyFollowingException.class, () -> userService.follow("follower", "followed"));
    }

    @Test
    public void FindByUsername_Success() {
        when(userRepository.findByUsername("cris6h16")).thenReturn(Optional.of(userModel));

        UserDTO result = userService.findByUsername("cris6h16");

        assertEquals(userDTO.getId(), result.getId());
        assertEquals(userDTO.getUsername(), result.getUsername());
    }

    @Test
    public void FindByUsername_UserNotFound() {
        when(userRepository.findByUsername("cris6h16")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findByUsername("cris6h16"));
    }

    @Test
    public void FindByUsernameFollowingEager_Success() {
        when(userRepository.findByUsername("cris6h16")).thenReturn(Optional.of(userModel));
        when(userRepository.findByUsernameFollowingEager("cris6h16")).thenReturn(Optional.of(userModel));

        UserDTO result = userService.findByUsernameFollowingEager("cris6h16");

        assertEquals(userDTO.getId(), result.getId());
        assertEquals(userDTO.getUsername(), result.getUsername());
    }

    @Test
    public void FindByUsernameFollowingEager_UserNotFound() {
        when(userRepository.findByUsernameFollowingEager("cris6h16")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findByUsernameFollowingEager("cris6h16"));
    }

}