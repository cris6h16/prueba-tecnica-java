package org.example.Domain.Model;

import org.example.Domain.Exceptions.Impls.UserModelException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Domain")
@Tag("UnitTest")
class UserModelTest {

    @Test
    public void constructorAndGetters() {
        Set<PostModel> posts = new HashSet<>();
        Set<UserModel> following = new HashSet<>();
        UserModel user = new UserModel("1", "cris6h16", posts, following);


        assertEquals("1", user.getId());
        assertEquals("cris6h16", user.getUsername());
        assertSame(posts, user.getPosts());
        assertSame(following, user.getFollowing());
    }

    @Test
    public void setId_Valid() {
        UserModel user = new UserModel("1", "cris6h16", new HashSet<>(), new HashSet<>());
        user.setId("  2  ");
        assertEquals("2", user.getId());
    }

    @ParameterizedTest
    @ValueSource(strings = {"null", "empty", "blank"})
    public void testSetId_Invalid(String id) {
        id = switch (id) {
            case "null" -> null;
            case "empty" -> "";
            case "blank" -> "   ";
            default -> id;
        };
        String finalId = id;
        UserModel user = new UserModel("1", "cris6h16", new HashSet<>(), new HashSet<>());
        assertThrows(UserModelException.class, () -> user.setId(finalId));
    }

    @Test
    public void setUsername_Valid() {
        UserModel user = new UserModel("1", "cris6h16", new HashSet<>(), new HashSet<>());
        user.setUsername("  newUsername  ");
        assertEquals("newUsername", user.getUsername());
    }

    @ParameterizedTest
    @ValueSource(strings = {"null", "empty", "blank"})
    public void setUsername_Invalid(String username) {
        username = switch (username) {
            case "null" -> null;
            case "empty" -> "";
            case "blank" -> "   ";
            default -> username;
        };
        String finalUsername = username;
        UserModel user = new UserModel("1", "cris6h16", new HashSet<>(), new HashSet<>());
        assertThrows(UserModelException.class, () -> user.setUsername(finalUsername));
    }

    @Test
    public void setPosts_Valid() {
        Set<PostModel> posts = new HashSet<>();
        UserModel user = new UserModel("1", "cris6h16", new HashSet<>(), new HashSet<>());
        user.setPosts(posts);
        assertSame(posts, user.getPosts());
    }

    @Test
    public void setPosts_Invalid() {
        UserModel user = new UserModel("1", "cris6h16", new HashSet<>(), new HashSet<>());
        assertThrows(UserModelException.class, () -> user.setPosts(null));
    }

    @Test
    public void setFollowing_Valid() {
        Set<UserModel> following = new HashSet<>();
        UserModel user = new UserModel("1", "cris6h16", new HashSet<>(), new HashSet<>());
        user.setFollowing(following);
        assertSame(following, user.getFollowing());
    }

    @Test
    public void setFollowing_Invalid() {
        UserModel user = new UserModel("1", "cris6h16", new HashSet<>(), new HashSet<>());
        assertThrows(UserModelException.class, () -> user.setFollowing(null));
    }

    @Test
    public void equals_SameId() {
        UserModel user1 = new UserModel("1", "username1", new HashSet<>(), new HashSet<>());
        UserModel user2 = new UserModel("1", "username2", new HashSet<>(), new HashSet<>());
        assertEquals(user1, user2);
    }

    @Test
    public void equals_DiffId() {
        UserModel user1 = new UserModel("1", "cris6h16", new HashSet<>(), new HashSet<>());
        UserModel user2 = new UserModel("2", "cris6h16", new HashSet<>(), new HashSet<>());
        assertNotEquals(user1, user2);
    }

    @Test
    public void equals_SameReference() {
        UserModel user = new UserModel("1", "cris6h16", new HashSet<>(), new HashSet<>());
        assertEquals(user, user);
    }

    @Test
    public void equals_Null() {
        UserModel user = new UserModel("1", "cris6h16", new HashSet<>(), new HashSet<>());
        assertNotEquals(user, null);
    }

    @Test
    public void testEquals_DifferentClasses() {
        UserModel user = new UserModel("1", "cris6h16", new HashSet<>(), new HashSet<>());
        assertNotEquals(user, new Object());
    }
}