package grup.service;

import grup.domain.Coins;
import grup.domain.User;
import grup.repo.RepoDBCoins;
import grup.repo.RepoDBUser;

import java.util.List;
import java.util.Optional;

public class ServiceCoins {

    private RepoDBCoins repo;

    public ServiceCoins(RepoDBCoins repo){
        this.repo = repo;
    }

    public List<Coins> findAllCoins(){
        return this.repo.findAll();
    }

}
