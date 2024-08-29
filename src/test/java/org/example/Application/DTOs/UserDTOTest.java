package org.example.Application.DTOs;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    @Test
    void constructorsArePrivate() {
        Constructor<UserDTO>[] constructors = (Constructor<UserDTO>[]) UserDTO.class.getDeclaredConstructors();
        for (Constructor<UserDTO> c : constructors) {
            assertFalse(c.canAccess(null));
        }
    }

    @Test
    void allArgsConstructor() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<UserDTO>[] cs = (Constructor<UserDTO>[]) UserDTO.class.getDeclaredConstructors();
        for (Constructor<UserDTO> c : cs) {
            if (c.getParameterCount() == 0) continue;
            c.setAccessible(true);

            Set<PostDTO> posts = new HashSet<>();
            Set<UserDTO> following = new HashSet<>();

            UserDTO userDTO = c.newInstance(
                    new UserDTO.Builder()
                            .setId("1")
                            .setUsername("cris6h16")
                            .setPosts(posts)
                            .setFollowing(following)
            );

            assertEquals("1", userDTO.getId());
            assertEquals("cris6h16", userDTO.getUsername());
            assertSame(posts, userDTO.getPosts());
            assertSame(following, userDTO.getFollowing());
        }
    }

    @Test
    void builderCreatesProperObject() {
        Set<PostDTO> posts = new HashSet<>();
        Set<UserDTO> following = new HashSet<>();

        UserDTO userDTO = new UserDTO.Builder()
                .setId("1")
                .setUsername("cris6h16")
                .setPosts(posts)
                .setFollowing(following)
                .build();

        assertEquals("1", userDTO.getId());
        assertEquals("cris6h16", userDTO.getUsername());
        assertSame(posts, userDTO.getPosts());
        assertSame(following, userDTO.getFollowing());
    }

    @Test
    void builderHandlesNullValues() {
        UserDTO userDTO = new UserDTO.Builder()
                .setId(null)
                .setUsername(null)
                .setPosts(null)
                .setFollowing(null)
                .build();

        assertNull(userDTO.getId());
        assertNull(userDTO.getUsername());
        assertNull(userDTO.getPosts());
        assertNull(userDTO.getFollowing());
    }

    @Test
    void gettersReturnCorrectValues() {
        Set<PostDTO> posts = new HashSet<>();
        Set<UserDTO> following = new HashSet<>();

        UserDTO userDTO = new UserDTO.Builder()
                .setId("1")
                .setUsername("cris6h16")
                .setPosts(posts)
                .setFollowing(following)
                .build();

        assertEquals("1", userDTO.getId());
        assertEquals("cris6h16", userDTO.getUsername());
        assertSame(posts, userDTO.getPosts());
        assertSame(following, userDTO.getFollowing());
    }
}
