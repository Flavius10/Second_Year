package grup.services;

import grup.domain.MenuItem;
import grup.repository.RepoDBMenu;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServiceMenuItem {

    private RepoDBMenu repoDBMenu;

    public ServiceMenuItem(RepoDBMenu repoDBMenu) {
        this.repoDBMenu = repoDBMenu;
    }

    public RepoDBMenu getRepoDBMenu() {
        return repoDBMenu;
    }

    public List<MenuItem> getAllMenuItems(){
        return repoDBMenu.findAll();
    }

    public Map<String, List<MenuItem>> getMenuGroupedByCategory(){
        return this.repoDBMenu.findAll().stream().collect(
                Collectors.groupingBy(MenuItem::getCategory)
        );
    }

}
