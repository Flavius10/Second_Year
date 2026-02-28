package grup.snipets.controller_example;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class ButtonController {

    // ==========================================================
    // ZONA 1: DECLARARE ELEMENTE UI (FXML)
    // ==========================================================
//    @FXML private CheckBox checkNegociabil;
//
//    @FXML private RadioButton radioDiesel;
//    @FXML private RadioButton radioBenzina;
//    @FXML private RadioButton radioElectric;
//
//    @FXML private ToggleGroup grupCombustibil; // Referinta la grup (Important pentru Radio)

    // ==========================================================
    // ZONA 2: METODE HANDLER (EXTRAGERE DATE)
    // ==========================================================
//    public void handleSave() {
//        // --- A. CUM SCOTI VALOAREA DIN CHECKBOX (Boolean: true/false) ---
//        boolean isNegotiable = checkNegociabil.isSelected();
//
//        // --- B. CUM SCOTI VALOAREA DIN RADIO BUTTON (String) ---
//
//        // Varianta 1: If-Else clasic (Mai sigur daca nu ai grup)
//        String combustibil = "";
//        if (radioDiesel.isSelected()) {
//            combustibil = "Diesel";
//        }
//        else if (radioBenzina.isSelected()) {
//            combustibil = "Benzina";
//        }
//        else if (radioElectric.isSelected()) {
//            combustibil = "Electric";
//        }
//
//        // Varianta 2: Varianta PRO (prin ToggleGroup)
//        // Atentie: Da eroare daca nu e nimic selectat, deci fa verificare null!
//        /*
//        RadioButton selected = (RadioButton) grupCombustibil.getSelectedToggle();
//        if (selected != null) {
//             String val = selected.getText();
//        }
//        */
//
//        System.out.println("Negociabil: " + isNegotiable);
//        System.out.println("Combustibil: " + combustibil);
//
//        // service.addCar(..., isNegotiable, combustibil);
//    }
}