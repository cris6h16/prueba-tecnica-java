package org.example.Domain.Model;

import org.example.Application.DTOs.CreatePostDTO;
import org.example.Domain.Exceptions.Impls.UserModelException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Tags({@Tag("Domain"), @Tag("UnitTest")})
class UserModelTest {

    @Test
    void constructorsArePrivate() {
        Constructor<UserModel>[] cs = (Constructor<UserModel>[]) CreatePostDTO.class.getDeclaredConstructors();
        for (Constructor<UserModel> c : cs) {
            assertFalse(c.canAccess(null));
        }
    }

    @Test
    void allArgsConstructorAndGetters() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<UserModel>[] cs = (Constructor<UserModel>[]) UserModel.class.getDeclaredConstructors();
        for (Constructor<UserModel> c : cs) {
            if (c.getParameterCount() == 0) continue;
            c.setAccessible(true);

            Set<UserModel> posts = new HashSet<>();
            Set<UserModel> following = new HashSet<>();
            UserModel mdoel = c.newInstance("1", "cris6h16", posts, following);

            assertEquals("1", mdoel.getId());
            assertEquals("cris6h16", mdoel.getUsername());
            assertSame(posts, mdoel.getPosts());
            assertSame(following, mdoel.getFollowing());
        }
    }

    @Test
    public void setId_Valid() {
        UserModel user = createValidUserModel("1", "cris6h16");
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
        UserModel user = createValidUserModel("1", "cris6h16");
        assertThrows(UserModelException.class, () -> user.setId(finalId));
    }

    @Test
    public void setUsername_Valid() {
        UserModel user = createValidUserModel("1", "cris6h16");
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
        UserModel user = createValidUserModel("1", "cris6h16");
        assertThrows(UserModelException.class, () -> user.setUsername(finalUsername));
    }

    @Test
    public void setPosts_Valid() {
        Set<PostModel> posts = new HashSet<>();
        UserModel user = createValidUserModel("1", "cris6h16");
        user.setPosts(posts);
        assertSame(posts, user.getPosts());
    }

    @Test
    public void setPosts_Invalid() {
        UserModel user = createValidUserModel("1", "cris6h16");
        assertThrows(UserModelException.class, () -> user.setPosts(null));
    }

    @Test
    public void setFollowing_Valid() {
        Set<UserModel> following = new HashSet<>();
        UserModel user = createValidUserModel("1", "cris6h16");
        user.setFollowing(following);
        assertSame(following, user.getFollowing());
    }

    @Test
    public void setFollowing_Invalid() {
        UserModel user = createValidUserModel("1", "cris6h16");
        assertThrows(UserModelException.class, () -> user.setFollowing(null));
    }

    @Test
    public void equals_SameId() {
        UserModel user1 = createValidUserModel("1", "cris6h16");
        UserModel user2 = createValidUserModel("1", "cris6h16");
        assertEquals(user1, user2);
    }

    @Test
    public void equals_DiffId() {
        UserModel user1 = createValidUserModel("1", "cris6h16");
        UserModel user2 = createValidUserModel("2", "cris6h16");
        assertNotEquals(user1, user2);
    }

    @Test
    public void equals_SameReference() {
        UserModel user = createValidUserModel("1", "cris6h16");
        assertEquals(user, user);
    }

    @Test
    public void equals_Null() {
        UserModel user = createValidUserModel("1", "cris6h16");
        assertNotEquals(user, null);
    }

    @Test
    public void testEquals_DifferentClasses() {
        UserModel user = createValidUserModel("1", "cris6h16");
        assertNotEquals(user, new Object());
    }

    @Test
    public void builder() {
        UserModel user = new UserModel.Builder()
                .setId("1231")
                .setUsername("https://github.com/cris6h16")
                .setPosts(new HashSet<>())
                .setFollowing(new HashSet<>())
                .build();
        assertEquals("1231", user.getId());
        assertEquals("https://github.com/cris6h16", user.getUsername());
        assertNotNull(user.getPosts());
        assertNotNull(user.getFollowing());
    }

    private UserModel createValidUserModel(String id, String username) {
        return new UserModel.Builder()
                .setId(id)
                .setUsername(username)
                .setPosts(new HashSet<>())
                .setFollowing(new HashSet<>())
                .build();
    }


}