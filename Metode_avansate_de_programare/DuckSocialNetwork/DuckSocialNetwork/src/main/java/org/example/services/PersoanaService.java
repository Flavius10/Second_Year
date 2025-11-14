package org.example.services;

import org.example.domain.Persoana;
import org.example.domain.User;
import org.example.exceptions.UserAlreadyExists;
import org.example.exceptions.UserNotFound;
import org.example.repositories.repo_db.RepoDBPersoana;
import org.example.repositories.repo_file.RepoFilePersoana;
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
     * @param repoDBPersoana the repo file persoana
     */
    public PersoanaService(RepoDBPersoana repoDBPersoana){
        this.repoDBPersoana = repoDBPersoana;
    }

    /**
     * Save person.
     * @param user      the user
     */
    public void savePerson(Persoana user){

        Optional<Persoana> saved = repoDBPersoana.save(user);
        if (saved.isPresent())
            throw new UserAlreadyExists("User already exists!");
    }

    /**
     * Delete person.
     * @param user      the user
     */
    public void deletePerson(Persoana user) {
        Optional<Persoana> deleted = this.repoDBPersoana.delete(user.getId());
        if (deleted.isEmpty()) {
            throw new UserNotFound("User not found!");
        }
    }

    /**
     * Update person.
     * @param user      the user
     */
    public void updatePerson(Persoana user){
        Optional<Persoana> updated = repoDBPersoana.update(user);
        if (updated.isPresent())
            throw new UserNotFound("User not found!");
    }

    /**
     * Find by id person user.
     * @param id        the id
     * @return the user
     */
    public User findByIdPerson(Long id){
        Optional<Persoana> persoana = this.repoDBPersoana.findOne(id);
        if (persoana.isPresent())
            return persoana.get();
        else
            throw new UserNotFound("User not found!");
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
     * @param username  the username
     * @return the user
     */
    public User findByUsernamePerson(String username){
        return repoDBPersoana.findByUsername(username).orElse(null);
    }

    public Page<Persoana> findAllOnPage(Pageable pageable) {

        return repoDBPersoana.findAllOnPage(pageable);
    }

}
