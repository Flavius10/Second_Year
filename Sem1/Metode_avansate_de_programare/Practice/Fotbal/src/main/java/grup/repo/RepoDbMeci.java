package grup.repo;

import grup.domain.Meci;
import grup.domain.User;
import grup.domain.UserType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepoDbMeci {

    private String url;
    private String username;
    private String password;

    public RepoDbMeci(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private Meci extractEntity(ResultSet rs) throws SQLException {

        String numeEchipaGazda = rs.getString("numeEchipaGazda");
        String numeEchipaOaspeti = rs.getString("numeEchipaOaspeti");
        Integer scorEchipaGazda = rs.getInt("scorEchipaGazda");
        Integer scorEchipaOaspeti = rs.getInt("scorEchipaOaspeti");

        return new Meci(numeEchipaGazda, numeEchipaOaspeti, scorEchipaGazda, scorEchipaOaspeti);
    }

    public List<Meci> findAll() {
        String query = "SELECT * FROM meciuri";
        List<Meci> items = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Meci item = new Meci(
                        rs.getString("numeEchipaGazda"),
                        rs.getString("numeEchipaOaspeti"),
                        rs.getInt("scorEchipaGazda"),
                        rs.getInt("scorEchipaOaspeti")
                );
                items.add(item);
            }
            return items;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
