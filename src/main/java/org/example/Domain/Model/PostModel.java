package org.example.Domain.Model;

import org.example.Domain.Exceptions.Impls.PostModelException;

/*
    No lo hago Value Object porque:
    - No es inmutable
    - Debe tener un id
    - su compariacion no se puede dar por igualdad de atributos
 */
public class PostModel {
    private String id;
    private String content;
    private Long instant;
    private UserModel user;

    private PostModel() {
    }

    private PostModel(String id, String content, Long instant, UserModel user) {
        this.setId(id);
        this.setContent(content);
        this.setInstant(instant);
        this.setUser(user);
    }

    // Getters && Setters

    public String getId() {
        return id;
    }


    public String getContent() {
        return content;
    }

    public Long getInstant() {
        return instant;
    }


    public UserModel getUser() {
        return user;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new PostModelException("Invalid id");
        }
        this.id = id.trim();
    }

    public void setContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new PostModelException("Invalid content, it can't be null or empty being trimmed");
        }
        this.content = content.trim();
    }

    public void setInstant(Long instant) {
        if (instant == null || instant < 0) {
            throw new PostModelException("Invalid instant");
        }
        this.instant = instant;
    }

    public void setUser(UserModel user) {
        if (user == null) {
            throw new PostModelException("Invalid user, it can't be null");
        }
        this.user = user;
    }

    public static class Builder {
        private String id;
        private String content;
        private Long instant;
        private UserModel user;

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

        public Builder setUser(UserModel user) {
            this.user = user;
            return this;
        }

        public PostModel build() {
            return new PostModel(
                    id,
                    content,
                    instant,
                    user
            );
        }
    }
}
