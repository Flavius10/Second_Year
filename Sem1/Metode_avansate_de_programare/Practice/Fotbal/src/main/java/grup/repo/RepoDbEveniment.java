package grup.repo;

import grup.domain.Eveniment;
import grup.domain.Meci;
import org.postgresql.core.ServerVersion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepoDbEveniment {

    private String url;
    private String username;
    private String password;

    public RepoDbEveniment(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private Eveniment extractEntity(ResultSet rs) throws SQLException {

        String numeEchipa = rs.getString("numeEchipa");
        Integer rataId = rs.getInt("rataId");
        String tipEveniment = rs.getString("tipEveniment");

        return new Eveniment(numeEchipa, rataId, tipEveniment);
    }

    public List<Eveniment> findAll() {
        String query = "SELECT * FROM eveniment";
        List<Eveniment> items = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Eveniment item = new Eveniment(
                        rs.getString("numeEchipa"),
                        rs.getInt("rataId"),
                        rs.getString("tipEveniment")
                );
                items.add(item);
            }
            return items;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Eveniment item) {
        String sql = "INSERT INTO eveniment (numeEchipa, rataId, tipEveniment) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, item.getNumeEchipa());
            ps.setInt(2, item.getRataId());
            ps.setString(3, item.getTipeEveniment());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
