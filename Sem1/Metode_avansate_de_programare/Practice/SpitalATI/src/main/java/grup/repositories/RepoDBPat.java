package grup.repositories;

import grup.domain.Pat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepoDBPat {

    private String url;
    private String username;
    private String password;

    public RepoDBPat(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public List<Pat> getAll(){
        List<Pat> lista = new ArrayList<>();
        String sql = "SELECT * FROM paturi ORDER BY id";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Pat(
                        rs.getInt("id"),
                        rs.getString("tip"),
                        rs.getBoolean("ventilatie"),
                        rs.getString("cnp_pacient")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void ocupaPat(int idPat, String cnpPacient) {
        String sql = "UPDATE paturi SET cnp_pacient = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnpPacient);
            stmt.setInt(2, idPat);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
