package grup.services;

import grup.domain.Pacient;
import grup.repositories.RepoDBPacient;

import java.util.List;

public class ServicePacient {

    private RepoDBPacient repo;

    public ServicePacient(RepoDBPacient repo) {
        this.repo = repo;
    }

    public List<Pacient> getPacientInAsteptare(){
        return repo.getAll();
    }

}
