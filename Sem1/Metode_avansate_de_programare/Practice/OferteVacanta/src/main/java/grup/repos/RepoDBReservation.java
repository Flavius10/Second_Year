package grup.repos;

import grup.domain.Reservation;
import java.sql.*;


public class RepoDBReservation {
    private String url, user, password;

    public RepoDBReservation(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void save(Reservation r) {
        String sql = "INSERT INTO reservations VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Generam un ID random sau luam unul nou (aici simplificam cu Random pt examen)
            stmt.setDouble(1, Math.random() * 10000);
            stmt.setLong(2, r.getClientId());
            stmt.setDouble(3, r.getHotelId());
            stmt.setTimestamp(4, Timestamp.valueOf(r.getStartDate()));
            stmt.setInt(5, r.getNoNights());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}