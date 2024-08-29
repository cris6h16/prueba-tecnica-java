package org.example.Domain.Model;

import org.example.Application.DTOs.CreatePostDTO;
import org.example.Domain.Exceptions.Impls.PostModelException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@Tags({@Tag("Domain"), @Tag("UnitTest")})
class PostModelTest {


    @Test
    void constructorsArePrivate() {
        Constructor<PostModel>[] cs = (Constructor<PostModel>[]) CreatePostDTO.class.getDeclaredConstructors();
        for (Constructor<PostModel> c : cs) {
            assertFalse(c.canAccess(null));
        }
    }

    @Test
    void allArgsConstructorAndGetters() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<PostModel>[] cs = (Constructor<PostModel>[]) PostModel.class.getDeclaredConstructors();
        for (Constructor<PostModel> c : cs) {
            if (c.getParameterCount() == 0) continue;
            c.setAccessible(true);

            UserModel user = createValidUserModel("1", "cris6h16");
            PostModel mdoel = c.newInstance("100", "content", 123456789L, user);

            assertEquals("100", mdoel.getId());
            assertEquals("content", mdoel.getContent());
            assertEquals(123456789L, mdoel.getInstant());
            assertEquals(user, mdoel.getUser());
        }
    }

    @Test
    public void builderAndGetters() {
        UserModel user = createValidUserModel("1", "cris6h16");
        PostModel post = new PostModel.Builder()
                .setId("1")
                .setContent("content")
                .setInstant(123456789L)
                .setUser(user)
                .build();

        assertEquals("1", post.getId());
        assertEquals("content", post.getContent());
        assertEquals(123456789L, post.getInstant());
        assertEquals(user, post.getUser());
    }

    @Test
    public void setId_Valid() {
        UserModel user = createValidUserModel("1", "cris6h16");
        PostModel post = new PostModel.Builder()
                .setId("1")
                .setContent("content")
                .setInstant(123456789L)
                .setUser(user)
                .build();

        post.setId(" 2 ");
        assertEquals("2", post.getId());
    }

    @ParameterizedTest
    @ValueSource(strings = {"null", "blank", "empty"})
    public void setId_Invalid(String value) {
        UserModel user = createValidUserModel("1", "cris6h16");
        PostModel post = new PostModel.Builder()
                .setId("1")
                .setContent("content")
                .setInstant(123456789L)
                .setUser(user)
                .build();
        value = switch (value) {
            case "null" -> null;
            case "blank" -> " ";
            case "empty" -> "";
            default -> "";
        };

        String finalValue = value;
        assertThrows(PostModelException.class, () -> post.setId(finalValue));
    }

    @Test
    public void setContent_Valid() {
        UserModel user = createValidUserModel("1", "cris6h16");
        PostModel post = new PostModel.Builder()
                .setId("1")
                .setContent("content")
                .setInstant(123456789L)
                .setUser(user)
                .build();

        post.setContent("  new content  ");
        assertEquals("new content", post.getContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"null", "blank", "empty"})
    public void setContent_Invalid(String value) {
        UserModel user = createValidUserModel("1", "cris6h16");
        PostModel post = new PostModel.Builder()
                .setId("1")
                .setContent("content")
                .setInstant(123456789L)
                .setUser(user)
                .build();
        value = switch (value) {
            case "null" -> null;
            case "blank" -> " ";
            case "empty" -> "";
            default -> "";
        };

        String finalValue = value;
        assertThrows(PostModelException.class, () -> post.setContent(finalValue));
    }

    @Test
    public void setInstant_Valid() {
        UserModel user = createValidUserModel("1", "cris6h16");
        PostModel post = new PostModel.Builder()
                .setId("1")
                .setContent("content")
                .setInstant(123456789L)
                .setUser(user)
                .build();

        post.setInstant(987654321L);
        assertEquals(987654321L, post.getInstant());
    }

    @ParameterizedTest
    @ValueSource(strings = {"null", "negative"})
    public void setInstant_Invalid(String value) {
        UserModel user = createValidUserModel("1", "cris6h16");
        PostModel post = new PostModel.Builder()
                .setId("1")
                .setContent("content")
                .setInstant(123456789L)
                .setUser(user)
                .build();
        value = switch (value) {
            case "null" -> null;
            case "negative" -> "-1";
            default -> "";
        };
        Long finalValue;
        if (value != null) {
             finalValue = Long.parseLong(value);
        } else {
            finalValue = null;
        }
        assertThrows(PostModelException.class, () -> post.setInstant(finalValue));
    }

    @Test
    public void setUser_Valid() {
        UserModel user1 = createValidUserModel("1", "cris6h16");
        UserModel user2 =  createValidUserModel("2", "cris6h16_2");
        PostModel post = new PostModel.Builder()
                .setId("1")
                .setContent("content")
                .setInstant(123456789L)
                .setUser(user1)
                .build();

        post.setUser(user2);
        assertEquals(user2, post.getUser());
    }

    @Test
    public void testSetUser_Invalid() {
        UserModel user = createValidUserModel("1", "cris6h16");
        PostModel post = new PostModel.Builder()
                .setId("1")
                .setContent("content")
                .setInstant(123456789L)
                .setUser(user)
                .build();

        assertThrows(PostModelException.class, () -> post.setUser(null));
    }

    @Test
    public void testBuilder() {
        UserModel user = createValidUserModel("1", "cris6h16");
        PostModel post = new PostModel.Builder()
                .setId("1")
                .setContent("content")
                .setInstant(123456789L)
                .setUser(user)
                .build();

        assertEquals("1", post.getId());
        assertEquals("content", post.getContent());
        assertEquals(123456789L, post.getInstant());
        assertEquals(user, post.getUser());
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
