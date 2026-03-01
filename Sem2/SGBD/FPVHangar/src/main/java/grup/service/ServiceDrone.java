package grup.service;

import grup.domain.Drone;
import grup.repo.RepoDrone;

import java.util.List;

public class ServiceDrone {

    private RepoDrone repoDrone;

    public ServiceDrone(RepoDrone repoDrone){
        this.repoDrone = repoDrone;
    }

    // Functie pentru a obtine toate dronele unui producator
    public List<Drone> getDronesByProducator(int producatorId){
        return repoDrone.getDronesByProducator(producatorId);
    }

    // Functie pentru a adauga o drona
    public void addDrone(Drone drona) throws Exception {
        if (drona.getNume_model() == null || drona.getNume_model().isEmpty()) {
            throw new Exception("Modelul dronei nu poate fi gol.");
        }
        if (drona.getGreutate_grame() <= 0) {
            throw new Exception("Greutatea dronei trebuie să fie un număr pozitiv.");
        }
        if (drona.getTip_cadru() == null || drona.getTip_cadru().isEmpty()) {
            throw new Exception("Cadrul dronei nu poate fi gol.");
        }
        repoDrone.addDrone(drona);
    }

    // Functie pentru a sterge o drona
    public void deleteDrone(int dronaId) throws Exception {
        this.repoDrone.deleteDrone(dronaId);
    }

    // Functie pentru a actualiza o drona
    public void updateDrone(Drone drona) throws Exception {
        if (drona.getNume_model() == null || drona.getNume_model().isEmpty()) {
            throw new Exception("Modelul dronei nu poate fi gol.");
        }
        if (drona.getGreutate_grame() <= 0) {
            throw new Exception("Greutatea dronei trebuie să fie un număr pozitiv.");
        }
        if (drona.getTip_cadru() == null || drona.getTip_cadru().isEmpty()) {
            throw new Exception("Cadrul dronei nu poate fi gol.");
        }
        repoDrone.updateDrone(drona);
    }

    // Functie pentru a obtine toate dronele
    public List<Drone> getAllDrones() {
        return repoDrone.getAllDrones();
    }



}
