package grup.snipets.service_example;

// IMPORTURILE TALE
import grup.utils.Observable;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.util.List;

public class ServiceAsync extends Observable {

//    private RepoGeneric repo;
//
//    public ServiceAsync(RepoGeneric repo) {
//        this.repo = repo;
//    }
//
//    // =============================================================
//    // 1. ADAUGARE (CREATE) - ASINCRON
//    // =============================================================
//    public void addAsync(Object entity) {
//        new Thread(() -> {
//            try {
//                System.out.println("--- [ADD] Start procesare... ---");
//
//                // 1. Simulam "serverul greu"
//                Thread.sleep(3000);
//
//                // 2. Validare (Optional, daca ai Strategy)
//                // strategy.validate(entity, repo.findAll());
//
//                // 3. Salvare efectiva
//                repo.save(entity);
//
//                // 4. Update UI (OBLIGATORIU in runLater)
//                Platform.runLater(() -> {
//                    notifyObservers(); // Refresh la tabel
//                    System.out.println("--- [ADD] Succes! ---");
//                });
//
//            } catch (Exception e) {
//                handleError("Eroare la adaugare", e);
//            }
//        }).start();
//    }
//
//    // =============================================================
//    // 2. ACTUALIZARE (UPDATE) - ASINCRON
//    // =============================================================
//    public void updateAsync(Object entity) {
//        new Thread(() -> {
//            try {
//                System.out.println("--- [UPDATE] Start procesare... ---");
//
//                // 1. Simulare intarziere
//                Thread.sleep(2000);
//
//                // 2. Update efectiv in DB
//                // Asigura-te ca ai metoda update in repo!
//                repo.update(entity);
//
//                // 3. Update UI
//                Platform.runLater(() -> {
//                    notifyObservers();
//                    System.out.println("--- [UPDATE] Modificat cu succes! ---");
//                });
//
//            } catch (Exception e) {
//                handleError("Eroare la modificare", e);
//            }
//        }).start();
//    }
//
//    // =============================================================
//    // 3. STERGERE (DELETE) - ASINCRON
//    // =============================================================
//    public void deleteAsync(Long idEntity) {
//        new Thread(() -> {
//            try {
//                System.out.println("--- [DELETE] Start stergere ID: " + idEntity + " ---");
//
//                // 1. Simulare
//                Thread.sleep(2000);
//
//                // 2. Stergere din DB
//                repo.delete(idEntity);
//
//                // 3. Update UI
//                Platform.runLater(() -> {
//                    notifyObservers();
//                    System.out.println("--- [DELETE] Sters cu succes! ---");
//                });
//
//            } catch (Exception e) {
//                handleError("Eroare la stergere", e);
//            }
//        }).start();
//    }
//
//    // =============================================================
//    // 4. METODA AJUTATOARE PENTRU ERORI (sa nu scrii cod dublu)
//    // =============================================================
//    private void handleError(String titlu, Exception e) {
//        e.printStackTrace(); // Sa vezi in consola
//        Platform.runLater(() -> {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Eroare Operatiune");
//            alert.setHeaderText(titlu);
//            alert.setContentText(e.getMessage());
//            alert.show();
//        });
//    }
//
//    // =============================================================
//    // 5. GET ALL (De obicei se face sincron, dar poti si asincron)
//    // =============================================================
//    public List<Object> findAll() {
//        return repo.findAll();
//    }
}