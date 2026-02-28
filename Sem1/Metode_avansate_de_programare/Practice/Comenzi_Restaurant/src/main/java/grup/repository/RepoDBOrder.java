package grup.repository;

import grup.domain.MenuItem;
import grup.domain.Order;
import grup.domain.OrderStatus;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RepoDBOrder {

    private String username;
    private String password;
    private String url;

    public RepoDBOrder(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void save(Order entity) {
        // 1. Salvam comanda (Header)
        String insertSQL = "INSERT INTO orders (id, table_id, date, status) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(insertSQL)) {

            statement.setInt(1, entity.getId());
            statement.setInt(2, entity.getTableId());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getDate()));
            statement.setString(4, entity.getStatus().toString());

            statement.executeUpdate();

            // 2. Salvam produsele (Items) - exact ca saveMembers din exemplul tau
            saveOrderItems(entity, connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveOrderItems(Order order, Connection connection) throws SQLException {
        if (order.getItems() == null || order.getItems().isEmpty()) return;

        String insertItemSQL = "INSERT INTO order_items (order_id, menu_item_id) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(insertItemSQL)) {
            for (MenuItem item : order.getItems()) {
                statement.setInt(1, order.getId());
                statement.setInt(2, item.getId());
                //Cu Batch: Pui toate ouăle în coș (addBatch), te duci la casă și le
                //scanezi pe toate odată (executeBatch). E o singură "călătorie" către baza de date.
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    public List<Order> findAll() {
        String query = "SELECT * FROM orders";
        List<Order> orders = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Integer id = rs.getInt("id");
                Integer tableId = rs.getInt("table_id");
                LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
                OrderStatus status = OrderStatus.valueOf(rs.getString("status"));

                // Incarcam produsele pentru comanda asta (Join manual)
                List<MenuItem> items = getOrderItems(id, connection);

                Order order = new Order(id, tableId, items, date, status);
                orders.add(order);
            }
            return orders;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<MenuItem> getOrderItems(Integer orderId, Connection connection) throws SQLException {
        String query = "SELECT m.* FROM menu_items m " +
                "JOIN order_items oi ON m.id = oi.menu_item_id " +
                "WHERE oi.order_id = ?";
        List<MenuItem> items = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                MenuItem item = new MenuItem(
                        rs.getInt("id"),
                        rs.getString("category"),
                        rs.getString("item"),
                        rs.getDouble("price"),
                        rs.getString("currency")
                );
                items.add(item);
            }
        }
        return items;
    }

}
