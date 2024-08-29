package org.example.Infrastructure.Config.SpringBoot;

import org.example.Application.Handlers.CreatePostCommandHandler;
import org.example.Application.Handlers.FindByUsernameCommandHandler;
import org.example.Application.Handlers.FindByUsernameFollowingEagerCommandHandler;
import org.example.Application.Handlers.FollowUserCommandHandler;
import org.example.Application.Services.Interfaces.PostApplicationService;
import org.example.Application.Services.Interfaces.UserApplicationService;
import org.example.Domain.Repositories.PostDomainRepository;
import org.example.Domain.Repositories.UserDomainRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/*
Mi clase de beans se llama Beans.java...
 */
@SpringBootTest
@ContextConfiguration(classes = Beans.class) /*"@ContextConfiguration defines class-level metadata that is used to determine how to load and configure an ApplicationContext for integration tests" extraido de docs*/
class BeansConfigurationTest {

    @Autowired
    private ApplicationContext ctx;

    @Test
    void contextLoads() {
        assertNotNull(ctx);
    }

    @Test
    void testBeansPresence() {
        assertNotNull(ctx.getBean(FollowUserCommandHandler.class)); // si tiene un alias, se le manda un argumentto mas
        assertNotNull(ctx.getBean(FindByUsernameFollowingEagerCommandHandler.class));
        assertNotNull(ctx.getBean(FindByUsernameCommandHandler.class));
        assertNotNull(ctx.getBean(CreatePostCommandHandler.class));
        assertNotNull(ctx.getBean(UserApplicationService.class));
        assertNotNull(ctx.getBean(PostApplicationService.class));
        assertNotNull(ctx.getBean(UserDomainRepository.class));
        assertNotNull(ctx.getBean(PostDomainRepository.class));
    }
}
