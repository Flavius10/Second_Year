package grup.utils;

import java.util.List;

public class PermissiveStrategy implements IValidationStrategy<Object> {

    @Override
    public void validate(Object entity, List<Object> repoData) throws Exception {
        // Aici nu facem nicio verificare complexa.
        // Totul trece!
        System.out.println("--- VALIDARE PERMISIVA: Totul e OK ---");

        // Sau poate doar un log simplu
        if (entity == null) {
            throw new Exception("Totusi... obiectul nu poate fi null.");
        }
    }
    //=========================================================
    //              ASA SE LEAGA STRATEGY PATTERN
    //=========================================================
//    public void add(Object entity) {
//        new Thread(() -> {
//            try {
//                // PASUL 1: Luam datele existente pentru comparatie
//                List<Object> dateExistent = repo.findAll();
//
//                // PASUL 2: ALEGEREA STRATEGIEI (Aici e cheia!)
//                IValidationStrategy<Object> strategy;
//
//                // CONDITIA TA GENERALA DE EXAMEN:
//                // Ex: Daca userul e 'ADMIN' -> Permissive
//                // Ex: Daca produsul e 'DISCOUNT' -> Strict
//                if (conditie_speciala_indeplinita) {
//                    strategy = new PermissiveStrategy();
//                } else {
//                    strategy = new StrictStrategy();
//                }
//
//                // PASUL 3: EXECUTIA (Daca pica, sare la catch)
//                strategy.validate(entity, dateExistent);
//
//                // PASUL 4: Daca a trecut de validare, salvam
//                Thread.sleep(3000);
//                repo.save(entity);
//
//                // Update UI
//                Platform.runLater(() -> notifyObservers());
//
//            } catch (Exception e) {
//                // Aici prindem eroarea aruncata de strategie
//                Platform.runLater(() -> {
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setContentText(e.getMessage());
//                    alert.show();
//                });
//            }
//        }).start();
//    }
}