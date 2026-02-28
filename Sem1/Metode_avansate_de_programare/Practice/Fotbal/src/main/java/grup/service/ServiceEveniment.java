package grup.service;

import grup.domain.Eveniment;
import grup.repo.RepoDbEveniment;
import grup.repo.RepoDbMeci;
import grup.utils.Observable;

import java.util.List;

public class ServiceEveniment extends Observable {

    private RepoDbEveniment repo;

    public ServiceEveniment(RepoDbEveniment repo){
        this.repo = repo;
    }

    public List<Eveniment> findAll() {
        return repo.findAll();
    }

    public void save(Eveniment eveniment) {
        new Thread(() -> {
            try {

                Thread.sleep(5000);

                // Facem update in DB
                repo.save(eveniment);


                javafx.application.Platform.runLater(() -> {
                    notifyObservers();
                    System.out.println("Item aprobat cu succes!");
                });

            } catch (Exception e) {
                e.printStackTrace();
                javafx.application.Platform.runLater(() -> {
                   System.err.println("Eroare la aprobare: " + e.getMessage());
                });
            }
        }).start();
    }

}
