package org.example.Infrastructure.Adapter.Input;

import org.example.Application.Commands.CreatePostCommand;
import org.example.Application.Commands.FindByUsernameCommand;
import org.example.Application.Commands.FindByUsernameFollowingEagerCommand;
import org.example.Application.Commands.FollowUserCommand;
import org.example.Application.DTOs.CreatePostDTO;
import org.example.Application.DTOs.PostDTO;
import org.example.Application.DTOs.UserDTO;
import org.example.Application.Handlers.CreatePostCommandHandler;
import org.example.Application.Handlers.FindByUsernameCommandHandler;
import org.example.Application.Handlers.FindByUsernameFollowingEagerCommandHandler;
import org.example.Application.Handlers.FollowUserCommandHandler;

import java.util.Date;
import java.util.Scanner;

public class ConsoleApi {
    private final FollowUserCommandHandler followUserCommandHandler;
    private final CreatePostCommandHandler createPostCommandHandler;
    private final FindByUsernameCommandHandler findByUsernameCommandHandler;
    private final FindByUsernameFollowingEagerCommandHandler findByUsernameFollowingEagerCommandHandler;

    public ConsoleApi(FollowUserCommandHandler followUserCommandHandler, CreatePostCommandHandler createPostCommandHandler, FindByUsernameCommandHandler findByUsernameCommandHandler, FindByUsernameFollowingEagerCommandHandler findByUsernameFollowingEagerCommandHandler) {
        this.followUserCommandHandler = followUserCommandHandler;
        this.createPostCommandHandler = createPostCommandHandler;
        this.findByUsernameCommandHandler = findByUsernameCommandHandler;
        this.findByUsernameFollowingEagerCommandHandler = findByUsernameFollowingEagerCommandHandler;
    }

    public void run() {
        System.out.println("Essribe 'exit' para salir... Ya puedes empezar a escribir comandos");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine().trim();
            try {
                if (command.startsWith("post")) post(command);
                else if (command.startsWith("follow")) follow(command);
                else if (command.startsWith("dashboard")) dashboard(command);
                else if (command.startsWith("wall")) wall(command);
                else if (command.equalsIgnoreCase("exit")) break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void wall(String command) {
        // wall @username
        command = command.trim();
        String username = command.split(" ")[1].replace("@", "");

        FindByUsernameCommand cmd = new FindByUsernameCommand(username);
        UserDTO me = this.findByUsernameCommandHandler.handle(cmd);

        for (PostDTO post : me.getPosts()) {
            System.out.println("\"" + post.getContent() + "\" @" + post.getUserUsername() + " @" + new Date(post.getInstant()).getHours() + ":" + new Date(post.getInstant()).getMinutes());
        }
    }

    private void dashboard(String command) {
        // dashboard @username
        command = command.trim();
        String username = command.split(" ")[1].replace("@", "");

        FindByUsernameFollowingEagerCommand cmd = new FindByUsernameFollowingEagerCommand(username);
        UserDTO me = this.findByUsernameFollowingEagerCommandHandler.handle(cmd);

        for (UserDTO user : me.getFollowing()) {
            for (PostDTO post : user.getPosts()) {
                System.out.println("\"" + post.getContent() + "\" @" + post.getUserUsername() + " @" + new Date(post.getInstant()).getHours() + ":" + new Date(post.getInstant()).getMinutes());
            }
        }
    }

    private void follow(String command) {
        // follow @username @username
        command = command.trim();
        String[] parts = command.split(" ");
        String follower = parts[1].replace("@", "");
        String followed = parts[2].replace("@", "");

        FollowUserCommand followUserCommand = new FollowUserCommand(follower, followed);
        this.followUserCommandHandler.handle(followUserCommand);
    }

    private void post(String command) {
        // post @username hola esto es un post
        command = command.trim();
        String[] parts = command.split(" ", 3);
        String username = parts[1].replace("@", "");
        String content = parts[2];

        CreatePostCommand createPostCommand = new CreatePostCommand(
                new CreatePostDTO.Builder()
                        .userUsername(username)
                        .content(content)
                        .build()
        );
        this.createPostCommandHandler.handle(createPostCommand);
    }
}
