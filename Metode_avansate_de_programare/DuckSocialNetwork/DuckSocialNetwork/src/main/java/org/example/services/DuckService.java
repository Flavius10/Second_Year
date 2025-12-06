package org.example.services;

import org.example.domain.TypeDuck;
import org.example.domain.ducks.Duck;
import org.example.domain.User;
import org.example.exceptions.UserAlreadyExists;
import org.example.exceptions.UserNotFound;
import org.example.repositories.repo_db.RepoDBDuck;
import org.example.utils.paging.Page;
import org.example.utils.paging.Pageable;

import java.util.Optional;

public class DuckService {

    private RepoDBDuck repoDBDuck;

    public DuckService(RepoDBDuck repoDBDuck){
        this.repoDBDuck = repoDBDuck;
    }

    public void saveDuck(Duck user){
        Optional<Duck> existing = repoDBDuck.findByUsername(user.getUsername());

        if (existing.isPresent()) {
            throw new UserAlreadyExists("User already exists!");
        }
        repoDBDuck.save(user);
    }

    public void deleteDuck(Duck user){
        Optional<Duck> result = this.repoDBDuck.delete(user.getId());
        if (result.isEmpty())
            throw new UserNotFound("User not found!");
    }

    public void updateDuck(Duck user){
        Optional<Duck> updated = repoDBDuck.update(user);
        if (updated.isEmpty()) {
            throw new UserNotFound("Nu s-a putut actualiza rata (nu a fost găsită în DB).");
        }
    }

    public User findByIdDuck(Long id) {
        return repoDBDuck.findOne(id)
                .orElseThrow(() -> new UserNotFound("User not found!"));
    }

    public Iterable<Duck> findAllDucks() {
        return repoDBDuck.findAll();
    }

    public Duck findByUsernameDuck(String username) {
        return repoDBDuck.findByUsername(username).orElse(null);
    }

    public Page<Duck> findAllOnPage(Pageable pageable) {
        return repoDBDuck.findAllOnPage(pageable);
    }

    public Iterable<Duck> findByType(TypeDuck type){
        return repoDBDuck.findByType(type);
    }
}