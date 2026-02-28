package grup.service;

import grup.domain.Coins;
import grup.repo.RepoDBCoins;

import java.util.List;

public class ServiceCoins {

    private RepoDBCoins repo;

    public ServiceCoins(RepoDBCoins repo){
        this.repo = repo;
    }

    public List<Coins> findAllCoins(){
        return this.repo.findAll();
    }

}
