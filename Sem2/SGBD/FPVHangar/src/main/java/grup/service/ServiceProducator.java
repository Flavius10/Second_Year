package grup.service;

import grup.domain.Producator;
import grup.repo.RepoProducator;

import java.util.List;

public class ServiceProducator {

    private RepoProducator repoProducator;

    public ServiceProducator(RepoProducator repoProducator) {
        this.repoProducator = repoProducator;
    }

    // Functie pentru a adauga un producator
    public List<Producator> getAllProducatori() {
        return repoProducator.getProducatori();
    }

}
