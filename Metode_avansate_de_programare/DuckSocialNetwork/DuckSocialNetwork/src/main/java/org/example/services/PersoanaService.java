package org.example.services;

import org.example.domain.User;
import org.example.exceptions.UserAlreadyExists;
import org.example.exceptions.UserNotFound;
import org.example.repositories.RepoFilePersoana;

public class PersoanaService {

    private RepoFilePersoana repoFilePersoana;

    public PersoanaService(RepoFilePersoana repoFilePersoana){
        this.repoFilePersoana = repoFilePersoana;
    }

    public void savePerson(User user, String file_name){

        try {
            this.repoFilePersoana.save(user, file_name);
        } catch (UserAlreadyExists e) {
            throw new UserAlreadyExists(e.getMessage());
        }
    }

    public void deletePerson(User user, String file_name){
        try{
            this.repoFilePersoana.delete(user, file_name);
        } catch(UserNotFound e){
            throw new UserNotFound(e.getMessage());
        }
    }

    public void updatePerson(User user, String file_name){
        try{
            this.repoFilePersoana.update(user, file_name);
        } catch(UserNotFound e){
            throw new UserNotFound(e.getMessage());
        }
    }

    public User findByIdPerson(Long id, String file_name){
        return this.repoFilePersoana.findById(id, file_name);
    }

    public Iterable<User> findAllPersons(String file_name){
        return this.repoFilePersoana.findAll(file_name);
    }

    public User findByUsernamePerson(String username, String file_name){
        return this.repoFilePersoana.findByUsername(username, file_name);
    }

}
