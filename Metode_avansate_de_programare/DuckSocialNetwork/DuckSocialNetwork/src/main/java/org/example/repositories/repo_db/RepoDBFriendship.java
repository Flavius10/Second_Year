package org.example.repositories.repo_db;

import org.example.domain.Entity;
import org.example.domain.Friendship;
import org.example.utils.paging.Page;
import org.example.utils.paging.Pageable;

import java.util.Optional;

public class RepoDBFriendship implements RepoDB<Long, Friendship>{

    @Override
    public Optional<Friendship> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<Friendship> findAll() {
        return null;
    }

    @Override
    public Optional<Friendship> save(Friendship entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Friendship> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Friendship> update(Friendship entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Friendship> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Page<Friendship> findAllOnPage(Pageable pageable) {
        return null;
    }
}
