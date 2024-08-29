package org.example.Infrastructure.Adapter.Output;

import org.example.Domain.Model.PostModel;
import org.example.Domain.Repositories.PostDomainRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryPostRepository implements PostDomainRepository {
    Map<String, PostModel> posts;

    public InMemoryPostRepository() {
        posts = new HashMap<>();
    }

    @Override
    public void create(PostModel post) {
        posts.put(post.getId(), post);
    }

    @Override
    public List<PostModel> findByUsername(String username) {
        return posts.values().stream()
                .filter(post -> post.getUser().getUsername().equals(username))
                .toList();
    }
}
