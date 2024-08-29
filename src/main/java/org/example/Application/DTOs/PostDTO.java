package org.example.Application.DTOs;

public class PostDTO {
    private String id;
    private String content;
    private Long instant;
    private String userUsername;

    private PostDTO(String id, String content, Long instant, String userUsername) {
        this.id = id;
        this.content = content;
        this.instant = instant;
        this.userUsername = userUsername;
    }

    private PostDTO() {
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Long getInstant() {
        return instant;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public static class Builder {
        private String id;
        private String content;
        private Long instant;
        private String userUsername;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setInstant(Long instant) {
            this.instant = instant;
            return this;
        }

        public Builder setUserUsername(String userUsername) {
            this.userUsername = userUsername;
            return this;
        }

        public PostDTO build() {
            return new PostDTO(
                    this.id,
                    this.content,
                    this.instant,
                    this.userUsername
            );
        }
    }
}