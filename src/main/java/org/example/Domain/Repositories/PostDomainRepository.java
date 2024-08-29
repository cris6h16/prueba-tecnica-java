package org.example.Domain.Repositories;

import org.example.Domain.Model.PostModel;

import java.util.List;

public interface PostDomainRepository {
    void create(PostModel post);
    List<PostModel> findByUsername(String username);
}
