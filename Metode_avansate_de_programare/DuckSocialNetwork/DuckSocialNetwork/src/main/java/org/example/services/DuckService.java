package org.example.services;

import org.example.domain.TypeDuck;
import org.example.domain.ducks.Duck;
import org.example.domain.User;
import org.example.exceptions.UserAlreadyExists;
import org.example.exceptions.UserNotFound;
import org.example.repositories.repo_db.RepoDBDuck;
import org.example.repositories.repo_file.RepoFileDuck;
import org.example.utils.paging.Page;
import org.example.utils.paging.Pageable;

import java.util.Optional;

/**
 * The type Duck service.
 */
public class DuckService {

    private RepoDBDuck repoDBDuck;

    /**
     * Instantiates a new Duck service.
     *
     * @param repoDBDuck the repo file duck
     */
    public DuckService(RepoDBDuck repoDBDuck){
        this.repoDBDuck = repoDBDuck;
    }

    /**
     * Save duck.
     *
     * @param user      the user
     */
    public void saveDuck(Duck user){

        Optional<Duck> result = repoDBDuck.save(user);
        if (result.isPresent())
            throw new UserAlreadyExists("User already exists!");
    }

    /**
     * Delete duck.
     *
     * @param user      the user
     */
    public void deleteDuck(Duck user){
        Optional<Duck> result = this.repoDBDuck.delete(user.getId());
        if (result.isEmpty())
            throw new UserNotFound("User not found!");
    }

    /**
     * Update duck.
     *
     * @param user      the user
     */
    public void updateDuck(Duck user){
        Optional<Duck> updated = repoDBDuck.update(user);
        if (updated.isPresent()) {
            throw new UserNotFound("Nu s-a putut actualiza rata (nu a fost găsită în DB).");
        }
    }

    /**
     * Find by id duck user.
     *
     * @param id        the id
     * @return the user
     */
    public User findByIdDuck(Long id) {
        return repoDBDuck.findOne(id).orElse(null);
    }

    /**
     * Find all ducks iterable.
     *
     * @return the iterable
     */
    public Iterable<Duck> findAllDucks() {
        return repoDBDuck.findAll();
    }

    /**
     * Find by username duck user.
     *
     * @param username  the username
     * @return the user
     */
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
