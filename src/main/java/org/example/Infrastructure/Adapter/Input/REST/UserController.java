package org.example.Infrastructure.Adapter.Input.REST;

import org.example.Application.Commands.FindByUsernameCommand;
import org.example.Application.Commands.FindByUsernameFollowingEagerCommand;
import org.example.Application.Commands.FollowUserCommand;
import org.example.Application.DTOs.PostDTO;
import org.example.Application.DTOs.UserDTO;
import org.example.Application.Exceptions.Impls.AlreadyFollowingException;
import org.example.Application.Exceptions.Impls.UserNotFoundException;
import org.example.Application.Exceptions.Impls.UsernameIsNullOrBlankException;
import org.example.Application.Handlers.FindByUsernameCommandHandler;
import org.example.Application.Handlers.FindByUsernameFollowingEagerCommandHandler;
import org.example.Application.Handlers.FollowUserCommandHandler;
import org.example.Infrastructure.Adapter.Input.REST.DTOs.FollowUserRequest;
import org.example.Infrastructure.Exceptions.REST.MyResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final FollowUserCommandHandler followUserCommandHandler;
    private final FindByUsernameCommandHandler findByUsernameCommandHandler;
    private final FindByUsernameFollowingEagerCommandHandler findByUsernameFollowingEagerCommandHandler;


    public UserController(FollowUserCommandHandler cmd1, FindByUsernameCommandHandler cmd2, FindByUsernameFollowingEagerCommandHandler cmd3) {
        this.followUserCommandHandler = cmd1;
        this.findByUsernameCommandHandler = cmd2;
        this.findByUsernameFollowingEagerCommandHandler = cmd3;
    }

    @PostMapping(
            value = "/follow",
            consumes = "application/json"
    )
    public ResponseEntity<Void> follow(@RequestBody FollowUserRequest request) {
        try {
            FollowUserCommand fcmd = new FollowUserCommand(request.getFollowerUsername(), request.getFollowedUsername());
            followUserCommandHandler.handle(fcmd);
            return ResponseEntity.ok().build();

        } catch (AlreadyFollowingException e) {
            throw new MyResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UserNotFoundException e) {
            throw new MyResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UsernameIsNullOrBlankException e) {
            throw new MyResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping(
            value = "/dashboard",
            produces = "application/json"
    )
    public ResponseEntity<Set<PostDTO>> dashboard(@RequestParam String username) {
        try {
            FindByUsernameFollowingEagerCommand fcmd = new FindByUsernameFollowingEagerCommand(username);
            UserDTO dto = findByUsernameFollowingEagerCommandHandler.handle(fcmd);

            Set<PostDTO> posts = dto.getFollowing().stream()
                    .flatMap(user -> user.getPosts().stream())// { {}, {}, {} } -> {}, {}, {}
                    .collect(Collectors.toSet());

            return ResponseEntity.ok(posts);

        } catch (UserNotFoundException e) {
            throw new MyResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UsernameIsNullOrBlankException e) {
            throw new MyResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping(
            value = "/wall",
            produces = "application/json"
    )
    public ResponseEntity<Set<PostDTO>> wall(@RequestParam String username) {
        try {
            FindByUsernameCommand fcmd = new FindByUsernameCommand(username);
            UserDTO dto = findByUsernameCommandHandler.handle(fcmd);

            return ResponseEntity.ok(dto.getPosts());

        } catch (UserNotFoundException e) {
            throw new MyResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UsernameIsNullOrBlankException e) {
            throw new MyResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


}
