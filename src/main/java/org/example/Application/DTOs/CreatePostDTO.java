package org.example.Application.DTOs;


public class CreatePostDTO {
    private String content;
    private String userUsername;

    private CreatePostDTO() {
    }

    private CreatePostDTO(String content, String userUsername) {
        this.content = content;
        this.userUsername = userUsername;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public static class Builder{
        private String content;
        private String userUsername;

        public Builder content(String content){
            this.content = content;
            return this;
        }

        public Builder userUsername(String userUsername){
            this.userUsername = userUsername;
            return this;
        }

        public CreatePostDTO build(){
            return new CreatePostDTO(content, userUsername);
        }
    }

}
