package org.example.services;

import org.example.domain.User;
import org.example.exceptions.UserAlreadyExists;
import org.example.exceptions.UserNotFound;
import org.example.repositories.RepoFileDuck;
import org.example.repositories.RepoFilePersoana;

/**
 * The type Duck service.
 */
public class DuckService {

    private RepoFileDuck repoFileDuck;

    /**
     * Instantiates a new Duck service.
     *
     * @param repoFileDuck the repo file duck
     */
    public DuckService(RepoFileDuck repoFileDuck){
        this.repoFileDuck = repoFileDuck;
    }

    /**
     * Save duck.
     *
     * @param user      the user
     * @param file_name the file name
     */
    public void saveDuck(User user, String file_name){

        try {
            this.repoFileDuck.save(user, file_name);
        } catch (UserAlreadyExists e) {
            throw new UserAlreadyExists(e.getMessage());
        }
    }

    /**
     * Delete duck.
     *
     * @param user      the user
     * @param file_name the file name
     */
    public void deleteDuck(User user, String file_name){
        try{
            this.repoFileDuck.delete(user, file_name);
        } catch(UserNotFound e){
            throw new UserNotFound(e.getMessage());
        }
    }

    /**
     * Update duck.
     *
     * @param user      the user
     * @param file_name the file name
     */
    public void updateDuck(User user, String file_name){
        try{
            this.repoFileDuck.update(user, file_name);
        } catch(UserNotFound e){
            throw new UserNotFound(e.getMessage());
        }
    }

    /**
     * Find by id duck user.
     *
     * @param id        the id
     * @param file_name the file name
     * @return the user
     */
    public User findByIdDuck(Long id, String file_name){
        return this.repoFileDuck.findById(id, file_name);
    }

    /**
     * Find all ducks iterable.
     *
     * @param file_name the file name
     * @return the iterable
     */
    public Iterable<User> findAllDucks(String file_name){
        return this.repoFileDuck.findAll(file_name);
    }

    /**
     * Find by username duck user.
     *
     * @param username  the username
     * @param file_name the file name
     * @return the user
     */
    public User findByUsernameDuck(String username, String file_name){
        return this.repoFileDuck.findByUsername(username, file_name);
    }

}
