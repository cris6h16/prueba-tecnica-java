package org.example.Domain.Services;

import org.example.Domain.Model.UserModel;

public interface UserDomainService {
    void follow(String followerUsername, String followedUsername);
}
