package org.example.Infrastructure.Config.SpringBoot;

import org.example.Application.Handlers.CreatePostCommandHandler;
import org.example.Application.Handlers.FindByUsernameCommandHandler;
import org.example.Application.Handlers.FindByUsernameFollowingEagerCommandHandler;
import org.example.Application.Handlers.FollowUserCommandHandler;
import org.example.Application.Services.Interfaces.PostApplicationService;
import org.example.Application.Services.Interfaces.UserApplicationService;
import org.example.Application.Services.PostApplicationServiceImpl;
import org.example.Application.Services.UserApplicationServiceImpl;
import org.example.Domain.Repositories.PostDomainRepository;
import org.example.Domain.Repositories.UserDomainRepository;
import org.example.Infrastructure.Adapter.Output.InMemoryPostRepository;
import org.example.Infrastructure.Adapter.Output.InMemoryUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {
    @Bean
    public FollowUserCommandHandler followUserCommandHandler(UserApplicationService service) {
        return new FollowUserCommandHandler(service);
    }

    @Bean
    public FindByUsernameFollowingEagerCommandHandler findByUsernameFollowingEagerCommandHandler(UserApplicationService service) {
        return new FindByUsernameFollowingEagerCommandHandler(service);
    }

    @Bean
    public FindByUsernameCommandHandler findByUsernameCommandHandler(UserApplicationService service) {
        return new FindByUsernameCommandHandler(service);
    }
    @Bean
    public CreatePostCommandHandler createPostCommandHandler(PostApplicationService service) {
        return new CreatePostCommandHandler(service);
    }

    @Bean
    public UserApplicationService userApplicationService(UserDomainRepository repo) {
        return new UserApplicationServiceImpl(repo);
    }
    @Bean
    public PostApplicationService postApplicationService(PostDomainRepository repoPost, UserDomainRepository repoUser) {
        return new PostApplicationServiceImpl(repoPost, repoUser);
    }

    @Bean
    public UserDomainRepository userDomainRepository() {
        return new InMemoryUserRepository();
    }

    @Bean
    public PostDomainRepository postDomainRepository() {
        return new InMemoryPostRepository();
    }
}
