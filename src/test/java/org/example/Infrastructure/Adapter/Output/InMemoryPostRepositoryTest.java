package org.example.Infrastructure.Adapter.Output;

import org.example.Domain.Model.PostModel;
import org.example.Domain.Model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryPostRepositoryTest {

    private InMemoryPostRepository repository;
    private PostModel post1;
    private PostModel post2;

    @BeforeEach
    void setUp() {
        repository = new InMemoryPostRepository();

        UserModel user1 = createValidUserModel("1", "cris6h16");
        UserModel user2 =  createValidUserModel("2", "cristianHerrera");

        post1 = createValidPostModel("post1", user1);
        post2 = createValidPostModel("post2", user2);
    }

    @Test
    void constructorInitializesEmptyMap() {
        assertNotNull(repository.posts);
        assertTrue(repository.posts.isEmpty());
    }

    @Test
    void createAddsPostToRepository() {
        repository.create(post1);
        assertEquals(1, repository.posts.size());
        assertEquals(post1, repository.posts.get(post1.getId()));
    }

    @Test
    void findByUsernameReturnsCorrectPosts() {
        repository.create(post1);
        repository.create(post2);

        List<PostModel> user1Posts = repository.findByUsername("cris6h16");
        List<PostModel> user2Posts = repository.findByUsername("cristianHerrera");

        assertEquals(1, user1Posts.size());
        assertTrue(user1Posts.contains(post1));
        assertFalse(user1Posts.contains(post2));

        assertEquals(1, user2Posts.size());
        assertTrue(user2Posts.contains(post2));
        assertFalse(user2Posts.contains(post1));
    }

    @Test
    void findByUsernameReturnsEmptyListWhenNoPostsFound() {
        List<PostModel> posts = repository.findByUsername("https://github.com/cris6h16");
        assertTrue(posts.isEmpty());
    }

    private UserModel createValidUserModel(String id, String username) {
        return new UserModel.Builder()
                .setId(id)
                .setUsername(username)
                .setPosts(new HashSet<>())
                .setFollowing(new HashSet<>())
                .build();
    }

    private PostModel createValidPostModel(String id, UserModel user) {
        return new PostModel.Builder()
                .setId(id)
                .setContent("content " + user.getUsername())
                .setInstant(123456789L)
                .setUser(user)
                .build();
    }
}
