package org.example.Application.Services;

import org.example.Application.DTOs.CreatePostDTO;
import org.example.Application.DTOs.PostDTO;
import org.example.Application.Exceptions.Impls.UserNotFoundException;
import org.example.Domain.Model.PostModel;
import org.example.Domain.Model.UserModel;
import org.example.Domain.Repositories.PostDomainRepository;
import org.example.Domain.Repositories.UserDomainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PostApplicationServiceImplTest {

    @Mock
    private PostDomainRepository postRepository;

    @Mock
    private UserDomainRepository userRepository;

    @InjectMocks
    private PostApplicationServiceImpl postService;

    private UserModel userModel;
    private PostModel postModel;
    private CreatePostDTO createPostDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userModel = new UserModel.Builder()
                .setId("1")
                .setUsername("cris6h16")
                .setPosts(new HashSet<>())
                .setFollowing(new HashSet<>())
                .build();

        postModel = new PostModel.Builder()
                .setId("1")
                .setContent("content")
                .setInstant(System.currentTimeMillis())
                .setUser(userModel)
                .build();
        createPostDTO = new CreatePostDTO.Builder()
                .content("content")
                .userUsername("cris6h16")
                .build();
    }

    @Test
    public void create_Success() {
        when(userRepository.findByUsername("cris6h16")).thenReturn(Optional.of(userModel));

        postService.create(createPostDTO);

        verify(postRepository).create(any(PostModel.class));
        assertFalse(userModel.getPosts().isEmpty());
    }

    @Test
    public void Create_UserNotFound() {
        when(userRepository.findByUsername("cris6h16")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> postService.create(createPostDTO));
    }

    @Test
    public void FindByUsername_Success() {
        when(postRepository.findByUsername("cris6h16")).thenReturn(Collections.singletonList(postModel));

        List<PostDTO> posts = postService.findByUsername("cris6h16");

        assertEquals(1, posts.size());
        PostDTO postDTO = posts.get(0);
        assertEquals("1", postDTO.getId());
        assertEquals("content", postDTO.getContent());
        assertEquals(postModel.getInstant(), postDTO.getInstant());
        assertEquals("cris6h16", postDTO.getUserUsername());
    }

    @Test
    public void FindByUsername_EmptyList() {
        when(postRepository.findByUsername("cris6h16")).thenReturn(Collections.emptyList());

        List<PostDTO> posts = postService.findByUsername("cris6h16");

        assertTrue(posts.isEmpty());
    }
}

