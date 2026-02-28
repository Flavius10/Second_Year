package grup.repo;

import grup.domain.User;
import grup.domain.UserType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepoDBUser {

    private String url;
    private String username;
    private String password;

    public RepoDBUser(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private User extractEntity(ResultSet rs) throws SQLException {

        String username = rs.getString("username");
        String password = rs.getString("password");
        UserType type = UserType.valueOf(rs.getString("type"));
        Integer buget = rs.getInt("buget");
        return new User(username, password, type, buget);
    }

    public List<User> findAll() {
        String query = "SELECT * FROM users";
        List<User> items = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                User item = new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        UserType.valueOf(rs.getString("type")),
                        rs.getInt("buget")
                );
                items.add(item);
            }
            return items;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findOne(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(url, this.username, this.password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    String username1 = rs.getString("username");
                    String password1 = rs.getString("password");
                    UserType type = UserType.valueOf(rs.getString("type"));
                    Integer buget = rs.getInt("buget");

                    User user = new User(username1, password1, type, buget);
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

}
