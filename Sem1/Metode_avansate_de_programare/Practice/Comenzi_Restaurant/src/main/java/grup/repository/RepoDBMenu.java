package grup.repository;

import grup.domain.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepoDBMenu {

    private String url;
    private String username;
    private String password;

    public RepoDBMenu(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public List<MenuItem> findAll() {
        String query = "SELECT * FROM menu_items";
        List<MenuItem> items = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

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
            return items;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(MenuItem item){
        String insertSQL = "INSERT INTO menu_items (id, category, item, price, currency) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(insertSQL)) {

            ps.setInt(1, item.getId());
            ps.setString(2, item.getCategory());
            ps.setString(3, item.getItem());
            ps.setDouble(4, item.getPrice());
            ps.setString(5, item.getCurrency());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
