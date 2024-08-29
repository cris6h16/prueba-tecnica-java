package org.example.Infrastructure.Adapter.Input.REST.DTOs;

public class CreatePostRequest {
    private String username;
    private String content;

    public CreatePostRequest(String username, String content) {
        this.username = username;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }
}
