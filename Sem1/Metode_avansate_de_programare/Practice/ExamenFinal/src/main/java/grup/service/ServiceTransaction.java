package grup.service;

import grup.domain.Transaction;
import grup.repo.RepoDBCoins;
import grup.repo.RepoDBTransaction;
import grup.utils.Observable;
import grup.utils.Observer;
import javafx.application.Platform;

import java.util.List;

public class ServiceTransaction extends Observable {

    private RepoDBTransaction repo;

    public ServiceTransaction(RepoDBTransaction repo){
        this.repo = repo;
    }

    public List<Transaction> findAllTransaction(){
        return this.repo.findAll();
    }

    public void addAsync(Transaction entity) {
        new Thread(() -> {
            try {
                System.out.println("--- [ADD] Start procesare... ---");

                Thread.sleep(3000);

                repo.save(entity);

                Platform.runLater(() -> {
                    notifyObservers();
                    System.out.println("--- [ADD] Succes! ---");
                });

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

}
