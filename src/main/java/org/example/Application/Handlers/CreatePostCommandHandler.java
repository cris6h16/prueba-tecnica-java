package org.example.Application.Handlers;

import org.example.Application.Commands.CreatePostCommand;
import org.example.Application.Services.Interfaces.PostApplicationService;

public class CreatePostCommandHandler {
    private final PostApplicationService postApplicationService;

    public CreatePostCommandHandler(PostApplicationService postApplicationService) {
        this.postApplicationService = postApplicationService;
    }

    public void handle(CreatePostCommand command) {
        postApplicationService.create(
                command.getCreatePostDTO()
        );
    }
}
