package org.example.Infrastructure.Adapter.Output;

import org.example.Domain.Model.UserModel;
import org.example.Domain.Repositories.UserDomainRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

public class InMemoryUserRepository implements UserDomainRepository {
    protected final Map<String, UserModel> users;

    public InMemoryUserRepository() {
        users = new HashMap<>();
        users.put("Alfonso", createValidUserModel("1", "Alfonso"));
        users.put("Ivan", createValidUserModel("2", "Ivan"));
        users.put("Alicia", createValidUserModel("3", "Alicia"));
    }

    @Override
    public void follow(String followerUsername, String followedUsername) {
        UserModel follower = users.get(followerUsername);
        UserModel followed = users.get(followedUsername);

        follower.getFollowing().add(followed);
    }

    // aqui deberia implementar la logica de Lazy Loading (simulacion) pero como es en memoria no es necesario
    @Override
    public Optional<UserModel> findByUsername(String username) {
        UserModel user = users.get(username);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<UserModel> findByUsernameFollowingEager(String username) {
        UserModel user = users.get(username);
        return Optional.ofNullable(user);
    }


    private UserModel createValidUserModel(String id, String username) {
        return new UserModel.Builder()
                .setId(id)
                .setUsername(username)
                .setPosts(new HashSet<>())
                .setFollowing(new HashSet<>())
                .build();
    }
}
