package grup.service;

import grup.domain.User;
import grup.repo.RepoDBUser;
import grup.utils.Observable;

import java.util.List;
import java.util.Optional;

public class ServiceUser extends Observable {

    private RepoDBUser repo;

    public ServiceUser(RepoDBUser repo){
        this.repo = repo;
    }

    public Optional<User> findByUsernamePassword(String username, String password){
        return this.repo.findOne(username, password);
    }

    public List<User> findAllUsers(){
        return this.repo.findAll();
    }
}
