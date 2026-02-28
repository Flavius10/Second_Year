package grup.repo;

import grup.domain.Coins;
import grup.domain.User;
import grup.domain.UserType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepoDBCoins {

    private String url;
    private String username;
    private String password;

    public RepoDBCoins(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public List<Coins> findAll() {
        String query = "SELECT * FROM coins";
        List<Coins> items = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Coins item = new Coins(
                        rs.getInt("pret"),
                        rs.getString("name")
                );
                items.add(item);
            }
            return items;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
