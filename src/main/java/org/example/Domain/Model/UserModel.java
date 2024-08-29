package org.example.Domain.Model;

import org.example.Domain.Exceptions.Impls.UserModelException;

import java.util.Set;


public class UserModel {
    private String id;
    private String username;
    private Set<PostModel> posts; // bidireccional
    private Set<UserModel> following;

    // todo: impl builder
    public UserModel(String id, String username, Set<PostModel> posts, Set<UserModel> following) {
        this.setId(id);
        this.setUsername(username);
        this.setPosts(posts);
        this.setFollowing(following);
    }


    // Getters && ( Setters -> validados )

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Set<PostModel> getPosts() {
        return posts;
    }


    public Set<UserModel> getFollowing() {
        return following;
    }


    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new UserModelException("Invalid id");
        }
        this.id = id.trim();
    }


    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new UserModelException("Invalid username, it can't be null or empty being trimmed");
        }
        this.username = username.trim();
    }


    public void setPosts(Set<PostModel> posts) {
        if (posts == null) {
            throw new UserModelException("Invalid posts, they can't be null");
        }
        this.posts = posts;
    }

    public void setFollowing(Set<UserModel> following) {
        if (following == null) {
            throw new UserModelException("Invalid following, it can't be null");
        }
        this.following = following;
    }

    // equals if id is the same
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        UserModel userModel = (UserModel) o;
        return id.equals(userModel.id);
    }
}
