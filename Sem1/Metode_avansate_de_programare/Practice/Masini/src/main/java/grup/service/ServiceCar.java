package grup.service;

import grup.domain.Car;
import grup.domain.CarStatus;
import grup.repo.RepoDBCar;
import grup.utils.Observable;

import java.util.List;

public class ServiceCar extends Observable {

    private RepoDBCar repo;

    public ServiceCar(RepoDBCar repo){
        this.repo = repo;
    }

    public void update(String username, String comentariu, CarStatus status){
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                repo.updateStatus(username, comentariu, status);

                javafx.application.Platform.runLater(() -> {
                    notifyObservers();
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public List<Car> findAllCars(){
        return this.repo.findAll();
    }

}
