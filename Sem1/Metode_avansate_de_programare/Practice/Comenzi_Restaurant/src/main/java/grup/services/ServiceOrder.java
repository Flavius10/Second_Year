package grup.services;

import grup.domain.MenuItem;
import grup.domain.Order;
import grup.domain.OrderStatus;
import grup.repository.RepoDBOrder;
import grup.utils.Observable;

import java.time.LocalDateTime;
import java.util.List;

public class ServiceOrder extends Observable {

    private RepoDBOrder repo;

    public ServiceOrder(RepoDBOrder repo) {
        this.repo = repo;
    }

    public List<Order> getAllOrders() {
        return repo.findAll();
    }

    public void placeOrder(int tableId, List<MenuItem> items){

        if (items == null || items.isEmpty()) {
            throw new RuntimeException("Nu se poate plasa o comanda fara produse!");
        }

        List<Order> allOrder = repo.findAll();
        int newId = 0;
        for(Order o: allOrder){
            if(o.getId() > newId){
                newId = o.getId();
            }
        }
        newId++;

        Order newOrder = new Order(
                newId,
                tableId,
                items,
                LocalDateTime.now(),
                OrderStatus.PLACED
        );

        this.repo.save(newOrder);

        notifyObservers();
    }

}
