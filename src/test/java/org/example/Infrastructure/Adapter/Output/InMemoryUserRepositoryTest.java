package org.example.Infrastructure.Adapter.Output;

import org.example.Domain.Model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryTest {

    private InMemoryUserRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryUserRepository();
    }

    @Test
    void constructorInitializesWithPredefinedUsers() {
        final Map<String, UserModel> users = repository.users;

        assertEquals(3, users.size());
        assertTrue(users.containsKey("Alfonso"));
        assertTrue(users.containsKey("Ivan"));
        assertTrue(users.containsKey("Alicia"));

        UserModel alfonso = users.get("Alfonso");
        assertEquals("1", alfonso.getId());
        assertEquals("Alfonso", alfonso.getUsername());
        assertTrue(alfonso.getPosts().isEmpty());
        assertTrue(alfonso.getFollowing().isEmpty());

        UserModel ivan = users.get("Ivan");
        assertEquals("2", ivan.getId());
        assertEquals("Ivan", ivan.getUsername());
        assertTrue(ivan.getPosts().isEmpty());
        assertTrue(ivan.getFollowing().isEmpty());

        UserModel alicia = users.get("Alicia");
        assertEquals("3", alicia.getId());
        assertEquals("Alicia", alicia.getUsername());
        assertTrue(alicia.getPosts().isEmpty());
        assertTrue(alicia.getFollowing().isEmpty());
    }

    @Test
    void followAddsUserToFollowing() {
        repository.follow("Alfonso", "Ivan");

        Optional<UserModel> alfonsoOpt = repository.findByUsername("Alfonso");
        Optional<UserModel> ivanOpt = repository.findByUsername("Ivan");

        assertTrue(alfonsoOpt.isPresent());
        assertTrue(ivanOpt.isPresent());

        UserModel alfonso = alfonsoOpt.get();
        UserModel ivan = ivanOpt.get();

        assertTrue(alfonso.getFollowing().contains(ivan));
    }

    @Test
    void findByUsernameReturnsUserIfExists() {
        Optional<UserModel> alfonsoOpt = repository.findByUsername("Alfonso");

        assertTrue(alfonsoOpt.isPresent());
        assertEquals("Alfonso", alfonsoOpt.get().getUsername());
    }

    @Test
    void findByUsernameReturnsEmptyIfUserNotFound() {
        Optional<UserModel> userOpt = repository.findByUsername("github.com/cris6h16");

        assertFalse(userOpt.isPresent());
    }

    @Test
    void findByUsernameFollowingEagerReturnsUserIfExists() {
        Optional<UserModel> ivanOpt = repository.findByUsernameFollowingEager("Ivan");

        assertTrue(ivanOpt.isPresent());
        assertEquals("Ivan", ivanOpt.get().getUsername());
    }

    @Test
    void findByUsernameFollowingEagerReturnsEmptyIfUserNotFound() {
        Optional<UserModel> userOpt = repository.findByUsernameFollowingEager("NonexistentUser");

        assertFalse(userOpt.isPresent());
    }
}
