package org.example.services;

import org.example.domain.User;
import org.example.exceptions.UserAlreadyExists;
import org.example.exceptions.UserNotFound;
import org.example.repositories.RepoFileDuck;
import org.example.repositories.RepoFilePersoana;

public class DuckService {

    private RepoFileDuck repoFileDuck;

    public DuckService(RepoFileDuck repoFileDuck){
        this.repoFileDuck = repoFileDuck;
    }

    public void saveDuck(User user, String file_name){

        try {
            this.repoFileDuck.save(user, file_name);
        } catch (UserAlreadyExists e) {
            throw new UserAlreadyExists(e.getMessage());
        }
    }

    public void deleteDuck(User user, String file_name){
        try{
            this.repoFileDuck.delete(user, file_name);
        } catch(UserNotFound e){
            throw new UserNotFound(e.getMessage());
        }
    }

    public void updateDuck(User user, String file_name){
        try{
            this.repoFileDuck.update(user, file_name);
        } catch(UserNotFound e){
            throw new UserNotFound(e.getMessage());
        }
    }

    public User findByIdDuck(Long id, String file_name){
        return this.repoFileDuck.findById(id, file_name);
    }

    public Iterable<User> findAllDucks(String file_name){
        return this.repoFileDuck.findAll(file_name);
    }

    public User findByUsernameDuck(String username, String file_name){
        return this.repoFileDuck.findByUsername(username, file_name);
    }

}
