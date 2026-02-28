package grup.repos;

import grup.domain.Hotel;
import grup.domain.HotelType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepoDBHotel {
    private String url;
    private String user;
    private String password;

    public RepoDBHotel(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    // Cerinta 1: Cautare hoteluri dintr-o locatie
    public List<Hotel> getHotelsByLocationId(Double locationId) {
        List<Hotel> list = new ArrayList<>();
        String sql = "SELECT * FROM hotels WHERE locationId = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, locationId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Hotel(
                        rs.getDouble("hotelId"),
                        rs.getDouble("locationId"),
                        rs.getString("hotelName"),
                        rs.getInt("noRooms"),
                        rs.getDouble("pricePerNight"),
                        HotelType.valueOf(rs.getString("type")) // Convertim String din DB in Enum
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Avem nevoie si de un findOne pentru rezervari mai tarziu
    public Hotel findOne(Double hotelId) {
        // (Il implementam doar daca e nevoie la final, momentan ne concentram pe cerinte)
        return null;
    }
}