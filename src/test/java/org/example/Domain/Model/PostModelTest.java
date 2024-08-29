package org.example.Domain.Model;

import org.example.Domain.Exceptions.Impls.PostModelException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tags({@Tag("Domain"), @Tag("UnitTest")})
class PostModelTest {


    @Test
    public void builderAndGetters() {
        UserModel user = new UserModel("1", "cris6h16", new HashSet<>(), new HashSet<>());
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
        UserModel user = new UserModel("1", "cris6h16", new HashSet<>(), new HashSet<>());
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
        UserModel user = new UserModel("1", "cris6h16", new HashSet<>(), new HashSet<>());
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
        UserModel user = new UserModel("1", "cris6h16", new HashSet<>(), new HashSet<>());
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
        UserModel user = new UserModel("1", "cris6h16", new HashSet<>(), new HashSet<>());
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
        UserModel user = new UserModel("1", "cris6h16", new HashSet<>(), new HashSet<>());
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
        UserModel user = new UserModel("1", "cris6h16", new HashSet<>(), new HashSet<>());
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
        UserModel user1 = new UserModel("1", "username1", new HashSet<>(), new HashSet<>());
        UserModel user2 = new UserModel("2", "username2", new HashSet<>(), new HashSet<>());
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
        UserModel user = new UserModel("1", "cris6h16", new HashSet<>(), new HashSet<>());
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
        UserModel user = new UserModel("1", "cris6h16", new HashSet<>(), new HashSet<>());
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
}
