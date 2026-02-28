package grup.controllers;

import grup.domain.Order;
import grup.services.ServiceOrder;
import grup.utils.Observable;
import grup.utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class StaffController implements Observer {

    @FXML
    private TableView<Order> tableOrders;

    private ServiceOrder serviceOrder;
    private ObservableList<Order> ordersData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        tableOrders.setItems(ordersData);
    }

    public void setService(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;

        update();
    }

    @Override
    public void update() {
        if (serviceOrder != null){
            ordersData.setAll(serviceOrder.getAllOrders());
        }
    }

}
