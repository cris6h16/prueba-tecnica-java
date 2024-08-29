package org.example.Main;

import org.example.Application.Handlers.CreatePostCommandHandler;
import org.example.Application.Handlers.FindByUsernameCommandHandler;
import org.example.Application.Handlers.FindByUsernameFollowingEagerCommandHandler;
import org.example.Application.Handlers.FollowUserCommandHandler;
import org.example.Application.Services.Interfaces.PostApplicationService;
import org.example.Application.Services.Interfaces.UserApplicationService;
import org.example.Application.Services.PostApplicationServiceImpl;
import org.example.Application.Services.UserApplicationServiceImpl;
import org.example.Infrastructure.Adapter.Input.ConsoleApi;
import org.example.Infrastructure.Adapter.Output.InMemoryPostRepository;
import org.example.Infrastructure.Adapter.Output.InMemoryUserRepository;

public class Application {

    public static void main(String[] args) {
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        InMemoryPostRepository postRepository = new InMemoryPostRepository();

        UserApplicationService userApplicationService = new UserApplicationServiceImpl(userRepository);
        PostApplicationService postApplicationService = new PostApplicationServiceImpl(postRepository, userRepository);

        var consoleApi = new ConsoleApi(
                new FollowUserCommandHandler(userApplicationService),
                new CreatePostCommandHandler(postApplicationService),
                new FindByUsernameCommandHandler(userApplicationService),
                new FindByUsernameFollowingEagerCommandHandler(userApplicationService)
        );
        consoleApi.run();
    }
}
