package org.example.Infrastructure.Adapter.Output;

import org.example.Domain.Model.UserModel;
import org.example.Domain.Repositories.UserDomainRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

public class InMemoryUserRepository implements UserDomainRepository {
    private final Map<String, UserModel> users;

    public InMemoryUserRepository() {
        users = new HashMap<>();
        users.put("Alfonso", new UserModel("1", "Alfonso", new HashSet<>(), new HashSet<>()));
        users.put("Ivan", new UserModel("2", "Ivan", new HashSet<>(), new HashSet<>()));
        users.put("Alicia", new UserModel("3", "Alicia", new HashSet<>(), new HashSet<>()));
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
}
