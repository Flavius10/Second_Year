package grup.repo;

import grup.domain.Coins;
import grup.domain.Transaction;
import grup.domain.User;
import grup.domain.UserType;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RepoDBTransaction {

    private String url;
    private String username;
    private String password;

    public RepoDBTransaction(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private Transaction extractEntity(ResultSet rs) throws SQLException {

        Integer id = rs.getInt("id");
        String nume = rs.getString("numeUtilizator");
        String numeCoin = rs.getString("coinName");
        String type = rs.getString("type");
        Integer price = rs.getInt("price");
        Timestamp ts = rs.getTimestamp("date_created");
        LocalDateTime date = (ts != null) ? ts.toLocalDateTime() : null;

       return new Transaction(id, nume, numeCoin, type, price, date);
    }

    public List<Transaction> findAll() {
        String query = "SELECT * FROM users";
        List<Transaction> items = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Transaction item = new Transaction(
                        rs.getInt("id"),
                        rs.getString("numeUtilizator"),
                        rs.getString("coinName"),
                        rs.getString("type"),
                        rs.getInt("price"),
                        rs.getTimestamp("date_created").toLocalDateTime()
                );
                items.add(item);
            }
            return items;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Transaction item) {
        String sql = "INSERT INTO transactions (id, numeUtilizator, coinName, type, price, timestamp) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            // Seteaza parametrii
            ps.setInt(1, item.getId());
            ps.setString(2, item.getNumeUtilizator());
            ps.setString(3, item.getCoinName());
            ps.setString(4, item.getType());
            ps.setInt(5, item.getPrice());

            if (item.getTimestamp() != null)
                ps.setTimestamp(6, Timestamp.valueOf(item.getTimestamp()));
            else
                ps.setTimestamp(6, null);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
