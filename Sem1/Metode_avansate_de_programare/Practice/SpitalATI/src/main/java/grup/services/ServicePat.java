package grup.services;

import grup.domain.Pat;
import grup.repositories.RepoDBPacient;
import grup.repositories.RepoDBPat;
import grup.utils.Observable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicePat extends Observable {

    private RepoDBPat repo;

    public ServicePat(RepoDBPat repo) {
        this.repo = repo;
    }

    public List<Pat> getAllPaturi() {
        return repo.getAll();
    }

    public Map<String, Integer> getStatisticaPaturiLibere() {
        Map<String, Integer> statistica = new HashMap<>();
        // Initializam cu 0 ca sa nu avem surprize
        statistica.put("TIC", 0);
        statistica.put("TIM", 0);
        statistica.put("TIIP", 0);
        statistica.put("TOTAL_OCUPATE", 0);

        List<Pat> toatePaturile = repo.getAll();

        for (Pat p : toatePaturile) {
            if (p.isLiber()) {
                // Daca e liber, crestem contorul pentru tipul respectiv (TIC/TIM/TIIP)
                String tip = p.getTip();
                if(statistica.containsKey(tip)) {
                    statistica.put(tip, statistica.get(tip) + 1);
                }
            } else {
                // Daca e ocupat, crestem totalul general
                statistica.put("TOTAL_OCUPATE", statistica.get("TOTAL_OCUPATE") + 1);
            }
        }
        return statistica;
    }

    public void interneazaPacient(String cnpPacient, Pat patSelectat) {

        // 1. Validare Pacient (sa nu fie null sau gol)
        if (cnpPacient == null || cnpPacient.trim().isEmpty()) {
            throw new RuntimeException("Eroare Validare: Nu ați selectat niciun pacient!");
        }

        // 2. Validare Pat (sa nu fie obiect null)
        if (patSelectat == null) {
            throw new RuntimeException("Eroare Validare: Nu ați selectat niciun pat!");
        }

        // 3. Validare Disponibilitate Pat (Cerinta 38)
        // Chiar daca in tabel pare liber, verificam proprietatea boolean/string
        if (!patSelectat.isLiber()) {
            throw new RuntimeException("Eroare Validare: Patul " + patSelectat.getId() +
                    " (" + patSelectat.getTip() + ") este DEJA OCUPAT!");
        }

        // Daca a trecut de filtrele de mai sus, e sigur sa facem update
        repo.ocupaPat(patSelectat.getId(), cnpPacient);

        // Notificam ferestrele
        notifyObservers();
    }

}
