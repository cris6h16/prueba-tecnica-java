package org.example.Application.DTOs;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class PostDTOTest {

    @Test
    void constructorsArePrivate()  {
        Constructor<PostDTO>[] constructors = (Constructor<PostDTO>[]) PostDTO.class.getDeclaredConstructors();
        for (Constructor<PostDTO> c : constructors) {
            assertFalse(c.canAccess(null));
        }
    }

    @Test
    void allArgsConstructor() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<PostDTO>[] cs = (Constructor<PostDTO>[]) PostDTO.class.getDeclaredConstructors();
        for (Constructor<PostDTO> c : cs) {
            if (c.getParameterCount() == 0) continue;
            c.setAccessible(true);
            PostDTO dto = c.newInstance("1", "content", 123456789L, "cris6h16");
            assertEquals("1", dto.getId());
            assertEquals("content", dto.getContent());
            assertEquals(123456789L, dto.getInstant());
            assertEquals("cris6h16", dto.getUserUsername());
        }
    }

    @Test
    void noArgsConstructor() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<PostDTO>[] cs = (Constructor<PostDTO>[]) PostDTO.class.getDeclaredConstructors();

        for (Constructor<PostDTO> c : cs) {
            if (c.getParameterCount() == 0) {
                c.setAccessible(true);
                PostDTO dto = c.newInstance();
                assertNotNull(dto);
            }
        }
    }

    @Test
    void builder() {
        PostDTO postDTO = new PostDTO.Builder()
                .setId("1")
                .setContent("content")
                .setInstant(123456789L)
                .setUserUsername("cris6h16")
                .build();

        assertEquals("1", postDTO.getId());
        assertEquals("content", postDTO.getContent());
        assertEquals(123456789L, postDTO.getInstant());
        assertEquals("cris6h16", postDTO.getUserUsername());
    }

    @Test
    void builderHandlesNullValues() {
        PostDTO postDTO = new PostDTO.Builder()
                .setId(null)
                .setContent(null)
                .setInstant(null)
                .setUserUsername(null)
                .build();

        assertNull(postDTO.getId());
        assertNull(postDTO.getContent());
        assertNull(postDTO.getInstant());
        assertNull(postDTO.getUserUsername());
    }

    @Test
    void gettersReturnCorrectValues() {
        PostDTO postDTO = new PostDTO.Builder()
                .setId("1")
                .setContent("content")
                .setInstant(123456789L)
                .setUserUsername("cris6h16")
                .build();

        assertEquals("1", postDTO.getId());
        assertEquals("content", postDTO.getContent());
        assertEquals(123456789L, postDTO.getInstant());
        assertEquals("cris6h16", postDTO.getUserUsername());
    }

}