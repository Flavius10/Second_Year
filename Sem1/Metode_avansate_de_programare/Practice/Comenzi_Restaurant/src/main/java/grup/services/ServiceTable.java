package grup.services;

import grup.domain.Table;
import grup.repository.RepoDBTable;

import java.util.List;

public class ServiceTable {

    private RepoDBTable repo;

    public ServiceTable(RepoDBTable repo) {
        this.repo = repo;
    }

    public List<Table> getAllTables() {
        return repo.findAll();
    }

}
