package org.example.Application.DTOs;

import java.util.Set;
public class UserDTO {
    private String id;
    private String username;
    private Set<PostDTO> posts; // bidireccional
    private Set<UserDTO> following;

    private UserDTO(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.posts = builder.posts;
        this.following = builder.following;
    }

    private UserDTO() {
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Set<PostDTO> getPosts() {
        return posts;
    }

    public Set<UserDTO> getFollowing() {
        return following;
    }

    // Builder class
    public static class Builder {
        private String id;
        private String username;
        private Set<PostDTO> posts;
        private Set<UserDTO> following;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPosts(Set<PostDTO> posts) {
            this.posts = posts;
            return this;
        }

        public Builder setFollowing(Set<UserDTO> following) {
            this.following = following;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(this);
        }
    }
}