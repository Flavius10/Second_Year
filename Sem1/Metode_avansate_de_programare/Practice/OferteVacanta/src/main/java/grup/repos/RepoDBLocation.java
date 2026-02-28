package grup.repos;

import grup.domain.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepoDBLocation {
    private String url;
    private String user;
    private String password;

    public RepoDBLocation(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public List<Location> getAll() {
        List<Location> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM locations");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Location(
                        rs.getDouble("locationId"),
                        rs.getString("locationName")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
