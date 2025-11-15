package org.example.repositories.repo_db;

import org.example.domain.card.Card;
import org.example.domain.ducks.Duck;
import org.example.utils.paging.Page;
import org.example.utils.paging.Pageable;

import java.util.Optional;

public class RepoDBCard<E extends Duck> implements RepoDB<Long, Card<E>> {


    @Override
    public Optional<Card<E>> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<Card<E>> findAll() {
        return null;
    }

    @Override
    public Optional<Card<E>> save(Card<E> entity) {
        return Optional.empty();
    }


    @Override
    public Optional<Card<E>> delete(Long aLong) {
        return Optional.empty();
    }


    @Override
    public Optional<Card<E>> update(Card<E> entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Card<E>> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Page<Card<E>> findAllOnPage(Pageable pageable) {
        return null;
    }
}
