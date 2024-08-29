package org.example.Application.Services;

import org.example.Application.DTOs.CreatePostDTO;
import org.example.Application.DTOs.PostDTO;
import org.example.Application.Exceptions.Impls.UserNotFoundException;
import org.example.Application.Services.Interfaces.PostApplicationService;
import org.example.Domain.Model.PostModel;
import org.example.Domain.Model.UserModel;
import org.example.Domain.Repositories.PostDomainRepository;
import org.example.Domain.Repositories.UserDomainRepository;

import java.util.Date;
import java.util.List;

public class PostApplicationServiceImpl implements PostApplicationService {
    private final PostDomainRepository postRepository;
    private final UserDomainRepository userRepository;

    public PostApplicationServiceImpl(PostDomainRepository postRepository, UserDomainRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void create(CreatePostDTO post) {
        UserModel u = userRepository.findByUsername(post.getUserUsername())
                .orElseThrow(() -> new UserNotFoundException(post.getUserUsername()));

        PostModel p = new PostModel.Builder() // attributes verified in the build() moment
                .setUser(u)
                .setContent(post.getContent())
                .setInstant(System.currentTimeMillis())
                .setId("-1")
                .build();

        u.getPosts().add(p); // add post to user - memory dir
        postRepository.create(p);
        System.out.println("@" + post.getUserUsername() + " posted _> \"" + post.getContent() + "\" @"+ new Date(p.getInstant()).getHours() + ":" + new Date(p.getInstant()).getMinutes());
    }

    @Override
    public List<PostDTO> findByUsername(String username) {
        return postRepository.findByUsername(username).stream()
                .map(p -> new PostDTO.Builder()
                        .setId(p.getId())
                        .setContent(p.getContent())
                        .setInstant(p.getInstant())
                        .setUserUsername(p.getUser().getUsername())
                        .build()
                )
                .toList();
    }

}
