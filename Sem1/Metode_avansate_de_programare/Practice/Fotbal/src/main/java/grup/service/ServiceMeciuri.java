package grup.service;

import grup.domain.Meci;
import grup.domain.User;
import grup.repo.RepoDbMeci;

import java.util.List;

public class ServiceMeciuri {

    private RepoDbMeci repo;

    public ServiceMeciuri(RepoDbMeci repo){
        this.repo = repo;
    }

    public List<Meci> findAllUsers(){
        return this.repo.findAll();
    }


}
