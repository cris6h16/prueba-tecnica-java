package org.example.Application.Services.Interfaces;


import org.example.Application.DTOs.CreatePostDTO;
import org.example.Application.DTOs.PostDTO;

import java.util.List;

public interface PostApplicationService {
    void create(CreatePostDTO post);
    List<PostDTO> findByUsername(String username);
}
