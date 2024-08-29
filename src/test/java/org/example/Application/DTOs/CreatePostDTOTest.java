package org.example.Application.DTOs;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class CreatePostDTOTest {

    @Test
    void constructorsArePrivate() {
        Constructor<CreatePostDTO>[] cs = (Constructor<CreatePostDTO>[]) CreatePostDTO.class.getDeclaredConstructors();
        for (Constructor<CreatePostDTO> c : cs) {
            assertFalse(c.canAccess(null));
        }
    }

    @Test
    void allArgsConstructorAndGetters() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<CreatePostDTO>[] cs = (Constructor<CreatePostDTO>[]) CreatePostDTO.class.getDeclaredConstructors();
        for (Constructor<CreatePostDTO> c : cs) {
            if (c.getParameterCount() == 0) continue;
            c.setAccessible(true);
            CreatePostDTO dto = c.newInstance("Test content.0", "cris6h16");
            assertEquals("Test content.0", dto.getContent());
            assertEquals("cris6h16", dto.getUserUsername());
        }
    }

    @Test
    void noArgsConstructor() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<CreatePostDTO>[] cs = (Constructor<CreatePostDTO>[]) CreatePostDTO.class.getDeclaredConstructors();

        for (Constructor<CreatePostDTO> c : cs) {
            if (c.getParameterCount() == 0) {
                c.setAccessible(true);
                CreatePostDTO dto = c.newInstance();
                assertNotNull(dto);
            }
        }
    }

    @Test
    void setContent() {
        CreatePostDTO dto = new CreatePostDTO.Builder().build();
        dto.setContent("Test content.1");
        assertEquals("Test content.1", dto.getContent());
    }

    @Test
    void setUserUsername() {
        CreatePostDTO dto = new CreatePostDTO.Builder().build();
        dto.setUserUsername("cris6h16");
        assertEquals("cris6h16", dto.getUserUsername());
    }

    @Test
    void testBuilderPattern() {
        CreatePostDTO dto = new CreatePostDTO.Builder()
                .content("Builder content")
                .userUsername("cris6h16_builder")
                .build();

        assertEquals("Builder content", dto.getContent());
        assertEquals("cris6h16_builder", dto.getUserUsername());
    }

}