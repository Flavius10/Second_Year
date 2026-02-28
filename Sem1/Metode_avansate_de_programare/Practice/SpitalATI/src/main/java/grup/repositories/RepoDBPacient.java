package grup.repositories;

import grup.domain.Pacient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepoDBPacient {

    private String url;
    private String username;
    private String password;

    public RepoDBPacient(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public List<Pacient> getAll() {
        List<Pacient> lista = new ArrayList<>();
        // Ordonam direct din SQL descrescator dupa gravitate (Cerinta 2)
        String sql = "SELECT * FROM pacienti ORDER BY gravitate DESC";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Pacient(
                        rs.getString("cnp"),
                        rs.getInt("varsta"),
                        rs.getBoolean("prematur"),
                        rs.getString("diagnostic_principal"),
                        rs.getInt("gravitate")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

