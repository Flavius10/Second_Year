package grup.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private Integer id;
    private Integer tableId;
    private List<MenuItem> items;
    private LocalDateTime date;
    private OrderStatus status;

    public Order(Integer id, Integer tableId, List<MenuItem> items, LocalDateTime date, OrderStatus status) {
        this.id = id;
        this.tableId = tableId;
        this.items = items;
        this.date = date;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public Integer getTableId() {
        return tableId;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", tableId=" + tableId +
                ", items=" + items +
                ", date=" + date +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return id != null ? id.equals(order.id) : order.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
