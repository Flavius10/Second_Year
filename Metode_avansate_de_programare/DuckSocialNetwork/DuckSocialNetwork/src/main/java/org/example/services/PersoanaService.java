package org.example.services;

import org.example.domain.Persoana;
import org.example.domain.User;
import org.example.exceptions.UserAlreadyExists;
import org.example.exceptions.UserNotFound;
import org.example.repositories.repo_db.RepoDBPersoana;
import org.example.utils.paging.Page;
import org.example.utils.paging.Pageable;

import java.util.Optional;

/**
 * The type Persoana service.
 */
public class PersoanaService {

    private RepoDBPersoana repoDBPersoana;

    /**
     * Instantiates a new Persoana service.
     * @param repoDBPersoana the repo db persoana
     */
    public PersoanaService(RepoDBPersoana repoDBPersoana){
        this.repoDBPersoana = repoDBPersoana;
    }

    /**
     * Save person.
     * @param user the user
     */
    public void savePerson(Persoana user){
        Optional<Persoana> existing = repoDBPersoana.findByUsername(user.getUsername());
        if (existing.isPresent()) {
            throw new UserAlreadyExists("User with username " + user.getUsername() + " already exists!");
        }

        repoDBPersoana.save(user);
    }

    /**
     * Delete person.
     * @param user the user
     */
    public void deletePerson(Persoana user) {
        Optional<Persoana> deleted = this.repoDBPersoana.delete(user.getId());

        if (deleted.isEmpty()) {
            throw new UserNotFound("User not found!");
        }
    }

    /**
     * Update person.
     * @param user the user
     */
    public void updatePerson(Persoana user){
        Optional<Persoana> updated = repoDBPersoana.update(user);


        if (updated.isEmpty()) {
            throw new UserNotFound("User not found (could not update)!");
        }
    }

    /**
     * Find by id person user.
     * @param id the id
     * @return the user
     */
    public User findByIdPerson(Long id){
        return this.repoDBPersoana.findOne(id)
                .orElseThrow(() -> new UserNotFound("User with id " + id + " not found!"));
    }

    /**
     * Find all persons iterable.
     * @return the iterable
     */
    public Iterable<Persoana> findAllPersons(){
        return this.repoDBPersoana.findAll();
    }

    /**
     * Find by username person user.
     *
     * @param username the username
     * @return the user
     */
    public User findByUsernamePerson(String username){
        return repoDBPersoana.findByUsername(username).orElse(null);
    }

    public Page<Persoana> findAllOnPage(Pageable pageable) {
        return repoDBPersoana.findAllOnPage(pageable);
    }
}