package grup.controller;

import grup.domain.User;
import grup.service.ServiceUser;
import grup.utils.Observer;
import javafx.fxml.FXML;

public class MainController implements Observer {


    private ServiceUser serviceUser;
    private User loggedInUser;

//    private ObservableList<Meci> menuData = FXCollections.observableArrayList();

    public void setService(ServiceUser serviceUser, User user) {
        this.serviceUser = serviceUser;
        this.loggedInUser = user;
    }


    @FXML
    public void initialize() {
//        tableMenu.setItems(menuData);
//
//        tableMenu.setOnMouseClicked(event -> {
//            Meci selectedMeci = tableMenu.getSelectionModel().getSelectedItem();
//            if (selectedMeci != null) {
//                openWindow("/fxml/afisare_view.fxml", "Detalii Meci", selectedMeci);
//            }
//        });
    }


    //    public List<Meci> selectMeciuri(){
//        List<Meci> meciuri = this.serviceMeciuri.findAllUsers();
//        return meciuri;
//    }
//
//    public void handleActionAdmin(){
//        try{
//            openWindow("/fxml/adauga_eveniment.fxml", "Adauga eveniment", null);
//        } catch (Exception e){
//            showMessage(Alert.AlertType.ERROR, "Nu merge", "Nu merge");
//        }
//
//    }
//
//    public void handleActionSpectator(){
//
//    }
//
    public void update() {
//        menuData.setAll(selectMeciuri());
//        tableMenu.setItems(menuData);
    }
//
//    private void showMessage(Alert.AlertType type, String header, String content) {
//        Alert alert = new Alert(type);
//        alert.setHeaderText(header);
//        alert.setContentText(content);
//        alert.showAndWait();
//    }

//    private void openWindow(String fxml, String title, Meci meci) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
//            Parent root = loader.load();
//
//            Object ctrl = loader.getController();
//
//            if (ctrl instanceof EvenimentController) {
//                ((EvenimentController) ctrl).setService(serviceEveniment);
//            } else{
//                ((AfisareController) ctrl).setService(serviceEveniment, meci);
//            }
//
//            Stage stage = new Stage();
//            stage.setTitle(title);
//            stage.setScene(new Scene(root));
//            stage.show();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
}

