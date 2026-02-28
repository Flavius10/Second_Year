package grup.repos;

import grup.domain.SpecialOffer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepoDBSpecialOffer {
    private String url, user, password;

    public RepoDBSpecialOffer(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public List<SpecialOffer> getOffersByHotel(Double hotelId) {
        List<SpecialOffer> list = new ArrayList<>();
        String sql = "SELECT * FROM special_offers WHERE hotelId = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, hotelId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new SpecialOffer(
                        rs.getDouble("specialOfferId"),
                        rs.getDouble("hotelId"),
                        rs.getDate("startDate").toLocalDate(), // SQL Date -> LocalDate
                        rs.getDate("endDate").toLocalDate(),
                        rs.getInt("percents")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}