package grup.snipets.controller_example;

//import grup.services.ServiceMenuItem;
//import grup.services.ServiceOrder;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Label;
//import javafx.scene.control.TableView;
//
//import java.util.ArrayList;
//import java.util.List;
//
public class TableController {
//
//    @FXML
//    private Label labelTableNumber;
//    @FXML
//    private TableView<MenuItem> tableMenu;
//    @FXML
//    private TableView<MenuItem> tableOrder;

//    @FXML
//    private ListView<Comment> listComentarii;

//
//    private ServiceMenuItem serviceMenu;
//    private ServiceOrder serviceOrder;
//    private int tableId;
//
//    private ObservableList<MenuItem> menuData = FXCollections.observableArrayList();
//    private ObservableList<MenuItem> orderData = FXCollections.observableArrayList();
//
//    @FXML
//    public void initialize(){
//        tableMenu.setItems(menuData);
//        tableOrder.setItems(orderData);
//    }
//
//    public void setService(ServiceMenuItem sMenu, ServiceOrder sOrder, int idMasa){
//        this.serviceMenu = sMenu;
//        this.serviceOrder = sOrder;
//        this.tableId = idMasa;
//        labelTableNumber.setText("Masa " + idMasa);
//        menuData.setAll(serviceMenu.getAllMenuItems());
//    }
//
//    @FXML
//    public void handleAdd(){
//        MenuItem selected = tableMenu.getSelectionModel()
//                .getSelectedItem();
//
//        if (selected != null){
//            orderData.add(selected);
//        } else {
//            showMessage(Alert.AlertType.WARNING,
//                    "Atentie",
//                    "Trebuie sa selectezi un produs din meniu!");
//        }
//    }

//    public void incarcaDetalii(Car car) {
//        // Presupunem ca service-ul returneaza lista de comentarii pentru masina asta
//        List<Comment> comments = service.getCommentsForCar(car.getId());
//
//        // Populam lista
//        listComentarii.getItems().setAll(comments);
//    }
//
//    @FXML
//    public void handlePlaceOrder(){
//        try{
//            List<MenuItem> items = new ArrayList<>(orderData);
//
//            serviceOrder.placeOrder(tableId, items);
//
//            showMessage(Alert.AlertType.INFORMATION, "Succes", "Comanda trimisa la bucatarie!");
//            orderData.clear();
//        } catch(RuntimeException e){
//            showMessage(Alert.AlertType.ERROR,
//                    "Eroare",
//                    e.getMessage());
//        }
//    }
//
//    private void showMessage(Alert.AlertType type, String header, String content) {
//        Alert alert = new Alert(type);
//        alert.setHeaderText(header);
//        alert.setContentText(content);
//        alert.showAndWait();
//    }

}
