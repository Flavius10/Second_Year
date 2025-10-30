package org.example.services;

import org.example.domain.Persoana;
import org.example.domain.User;
import org.example.exceptions.UserAlreadyExists;
import org.example.exceptions.UserNotFound;
import org.example.repositories.repo_file.RepoFilePersoana;

/**
 * The type Persoana service.
 */
public class PersoanaService {

    private RepoFilePersoana repoFilePersoana;

    /**
     * Instantiates a new Persoana service.
     *
     * @param repoFilePersoana the repo file persoana
     */
    public PersoanaService(RepoFilePersoana repoFilePersoana){
        this.repoFilePersoana = repoFilePersoana;
    }

    /**
     * Save person.
     *
     * @param user      the user
     * @param file_name the file name
     */
    public void savePerson(Persoana user, String file_name){

        try {
            this.repoFilePersoana.save(user, file_name);
        } catch (UserAlreadyExists e) {
            throw new UserAlreadyExists(e.getMessage());
        }
    }

    /**
     * Delete person.
     *
     * @param user      the user
     * @param file_name the file name
     */
    public void deletePerson(Persoana user, String file_name){
        try{
            this.repoFilePersoana.delete(user, file_name);
        } catch(UserNotFound e){
            throw new UserNotFound(e.getMessage());
        }
    }

    /**
     * Update person.
     *
     * @param user      the user
     * @param file_name the file name
     */
    public void updatePerson(Persoana user, String file_name){
        try{
            this.repoFilePersoana.update(user, file_name);
        } catch(UserNotFound e){
            throw new UserNotFound(e.getMessage());
        }
    }

    /**
     * Find by id person user.
     *
     * @param id        the id
     * @param file_name the file name
     * @return the user
     */
    public User findByIdPerson(Long id, String file_name){
        return this.repoFilePersoana.findById(id, file_name);
    }

    /**
     * Find all persons iterable.
     *
     * @param file_name the file name
     * @return the iterable
     */
    public Iterable<Persoana> findAllPersons(String file_name){
        return this.repoFilePersoana.findAll(file_name);
    }

    /**
     * Find by username person user.
     *
     * @param username  the username
     * @param file_name the file name
     * @return the user
     */
    public User findByUsernamePerson(String username, String file_name){
        return this.repoFilePersoana.findByUsername(username, file_name);
    }

}
