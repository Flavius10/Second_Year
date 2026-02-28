package grup.snipets.controller_example;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;

public class EditController {

    // ==========================================================
    // ZONA 1: DECLARARE ELEMENTE UI (FXML)
    // ==========================================================
//    @FXML private TextField inputString;
//    @FXML private TextField inputNumber;
//    @FXML private ComboBox<String> inputCombo;
//    @FXML private DatePicker inputDate;
//    @FXML private CheckBox inputCheck;

    // ==========================================================
    // ZONA 2: DEPENDINTE SI STATE (ENTITATEA)
    // ==========================================================
//    private Entitate entity; // Daca e null = ADD, altfel = UPDATE
//    // private ServiceService service; // Daca ai nevoie de service aici

    // ==========================================================
    // ZONA 3: INITIALIZARE SI POPULARE FORMULAR
    // ==========================================================
//    public void setService(Entitate entity) {
//        this.entity = entity;
//
//        // A. POPULARE COMBOBOX (Valori posibile)
//        inputCombo.getItems().addAll("STATUS_1", "STATUS_2", "STATUS_3");
//
//        // B. DACA E UPDATE, UMPLEM CAMPURILE CU DATELE VECHI
//        if (entity != null) {
//            inputString.setText(entity.getNume());
//            inputNumber.setText(String.valueOf(entity.getPret()));
//            inputCombo.setValue(entity.getStatus());
//            inputDate.setValue(entity.getData().toLocalDate());
//            inputCheck.setSelected(entity.isActiv());
//        }
//    }

    // ==========================================================
    // ZONA 4: SALVARE (SAVE / UPDATE)
    // ==========================================================
//    @FXML
//    public void handleSave() {
//        try {
//            // PAS 1. EXTRACTIE DATE DIN UI
//            String nume = inputString.getText();
//            String pretStr = inputNumber.getText();
//            String status = inputCombo.getValue();
//            LocalDate data = inputDate.getValue();
//            boolean activ = inputCheck.isSelected();
//
//            // PAS 2. VALIDARE SIMPLA (Campuri goale)
//            if (nume.isEmpty() || pretStr.isEmpty() || status == null || data == null) {
//                // MessageAlert.showErrorMessage(null, "Toate campurile sunt obligatorii!");
//                return;
//            }
//
//            // PAS 3. CONVERSIE (String -> Double/Int)
//            double pret = Double.parseDouble(pretStr);
//
//            // PAS 4. LOGICA DE SALVARE
//            if (entity == null) {
//                // LOGICA ADD: service.save(nume, pret, ...);
//            } else {
//                // LOGICA UPDATE: service.update(entity.getId(), nume, ...);
//            }
//
//            // PAS 5. INCHIDE FEREASTRA
//            ((Stage) inputString.getScene().getWindow()).close();
//
//        } catch (NumberFormatException e) {
//            System.out.println("Eroare: Pretul nu e un numar valid!");
//            // MessageAlert.showErrorMessage(null, "Pretul trebuie sa fie numar!");
//        }
//    }
}