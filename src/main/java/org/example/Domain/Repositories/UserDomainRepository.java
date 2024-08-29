package org.example.Domain.Repositories;

import org.example.Domain.Model.UserModel;

import java.util.Optional;

public interface UserDomainRepository {
    void follow(String followerUsername, String followedUsername);
    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findByUsernameFollowingEager(String username);
}
