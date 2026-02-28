package grup.repository;

import grup.domain.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepoDBTable {

    private String username;
    private String password;
    private String url;

    public RepoDBTable(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public List<Table> findAll() {
        String query = "SELECT * FROM tables";
        List<Table> tables = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                tables.add(new Table(rs.getInt("id")));
            }
            return tables;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
