package org.example.Application.Services;

import org.example.Application.DTOs.PostDTO;
import org.example.Application.DTOs.UserDTO;
import org.example.Application.Exceptions.Impls.AlreadyFollowingException;
import org.example.Application.Exceptions.Impls.UserNotFoundException;
import org.example.Application.Exceptions.Impls.UsernameIsNullOrBlankException;
import org.example.Application.Services.Interfaces.UserApplicationService;
import org.example.Domain.Model.UserModel;
import org.example.Domain.Repositories.UserDomainRepository;

import java.util.stream.Collectors;

public class UserApplicationServiceImpl implements UserApplicationService {

    private final UserDomainRepository userRepository;

    public UserApplicationServiceImpl(UserDomainRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* evito verificaciones como:
    - que se siga a si mismo
    - etc
     */
    @Override
    public void follow(String followerUsername, String followedUsername) {
        existsUsername(followerUsername);
        existsUsername(followedUsername);
        isNotFollowing(followerUsername, followedUsername);

        userRepository.follow(followerUsername, followedUsername);
        System.out.println("@" + followerUsername + " empezó a seguir a @" + followedUsername);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public UserDTO findByUsername(String username) {
        existsUsername(username);
        return userRepository.findByUsername(username)
                .map(this::convertToDTO)
                .get();
    }

    private void isNotFollowing(String followerUsername, String followedUsername) {
        UserModel follower = userRepository.findByUsername(followerUsername)
                .orElseThrow(() -> new UserNotFoundException(followerUsername));

        UserModel followed = userRepository.findByUsername(followedUsername)
                .orElseThrow(() -> new UserNotFoundException(followedUsername));

        if (follower.getFollowing().contains(followed)) {
            throw new AlreadyFollowingException(followerUsername, followedUsername);
        }
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public UserDTO findByUsernameFollowingEager(String username) {
        existsUsername(username);
        return userRepository.findByUsernameFollowingEager(username)
                .map(this::convertToDTO)
                .get();
    }

    private void existsUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new UsernameIsNullOrBlankException();
        }
        userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    private UserDTO convertToDTO(UserModel userModel) {
        return new UserDTO.Builder()
                .setId(userModel.getId())
                .setUsername(userModel.getUsername())
                .setFollowing(
                        userModel.getFollowing().stream()
                                .map(this::convertToDTO)
                                .collect(Collectors.toSet())
                )
                .setPosts(
                        userModel.getPosts().stream().map(post -> new PostDTO.Builder()
                                .setId(post.getId())
                                .setContent(post.getContent())
                                .setInstant(post.getInstant())
                                .setUserUsername(post.getUser().getUsername())
                                .build()).collect(Collectors.toSet())
                )
                .build();
    }
}
