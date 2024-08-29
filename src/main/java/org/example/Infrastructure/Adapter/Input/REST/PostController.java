package org.example.Infrastructure.Adapter.Input.REST;

import org.example.Application.Commands.CreatePostCommand;
import org.example.Application.DTOs.CreatePostDTO;
import org.example.Application.Exceptions.Impls.UserNotFoundException;
import org.example.Application.Handlers.CreatePostCommandHandler;
import org.example.Infrastructure.Exceptions.REST.MyResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final CreatePostCommandHandler createPostCommandHandler;

    public PostController(CreatePostCommandHandler createPostCommandHandler) {
        this.createPostCommandHandler = createPostCommandHandler;
    }

    @PostMapping(
            value = "/create",
            consumes = "application/json"
    )
    public ResponseEntity<Void> create(@RequestBody String username, @RequestBody String content) {
        try {
            CreatePostCommand cpcmd = new CreatePostCommand(new CreatePostDTO.Builder()
                    .userUsername(username)
                    .content(content)
                    .build()
            );
            createPostCommandHandler.handle(cpcmd);
            return ResponseEntity.ok().build();

        } catch (UserNotFoundException e) {
            throw new MyResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
