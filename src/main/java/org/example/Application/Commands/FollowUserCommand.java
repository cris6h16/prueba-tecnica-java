package org.example.Application.Commands;

public class FollowUserCommand {
    private final String followerUsername;
    private final String followedUsername;

    public FollowUserCommand(String followerUsername, String followedUsername) {
        this.followerUsername = followerUsername;
        this.followedUsername = followedUsername;
    }

    public String getFollowerUsername() {
        return followerUsername;
    }

    public String getFollowedUsername() {
        return followedUsername;
    }
}
