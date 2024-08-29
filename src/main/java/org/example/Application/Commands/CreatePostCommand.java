package org.example.Application.Commands;

import org.example.Application.DTOs.CreatePostDTO;

public class CreatePostCommand {
    private final CreatePostDTO createPostDTO;

    public CreatePostCommand(CreatePostDTO createPostDTO) {
        this.createPostDTO = createPostDTO;
    }

        public CreatePostDTO getCreatePostDTO() {
        return createPostDTO;
    }
}
