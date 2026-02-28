package grup.repos;

import grup.domain.Client;
import grup.domain.Hobby;

import java.sql.*;

public class RepoDBClient {
    private String url, user, password;

    public RepoDBClient(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Client findOne(Long clientId) {
        String sql = "SELECT * FROM clients WHERE clientId = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, clientId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Client(
                        rs.getLong("clientId"),
                        rs.getString("name"),
                        rs.getInt("fidelityGrade"),
                        rs.getInt("varsta"),
                        Hobby.valueOf(rs.getString("hobbies"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}