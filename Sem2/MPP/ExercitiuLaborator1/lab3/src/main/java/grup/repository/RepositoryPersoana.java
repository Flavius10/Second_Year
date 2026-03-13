package grup.repository;

import grup.domain.Persoana;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositoryPersoana {

    private String url;

    public RepositoryPersoana(String url) {
        this.url = url;
    }

    public void adauga(Persoana p) {
        String sql = "INSERT INTO Persoana(id_persoana, nume, prenume, varsta) VALUES(?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, p.getId_persoana());
            pstmt.setString(2, p.getNume());
            pstmt.setString(3, p.getPrenume());
            pstmt.setInt(4, p.getVarsta());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Eroare la adaugare: " + e.getMessage());
        }
    }

    public void modifica(Persoana p) {
        String sql = "UPDATE Persoana SET nume = ?, prenume = ?, varsta = ? WHERE id_persoana = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getNume());
            pstmt.setString(2, p.getPrenume());
            pstmt.setInt(3, p.getVarsta());
            pstmt.setLong(4, p.getId_persoana());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Eroare la modificare: " + e.getMessage());
        }
    }

    public List<Persoana> gasesteDupaConditie(int varstaMinima) {
        String sql = "SELECT * FROM Persoana WHERE varsta > ?";
        List<Persoana> rezultat = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, varstaMinima);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Persoana p = new Persoana(
                        rs.getLong("id_persoana"),
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getInt("varsta")
                );
                rezultat.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Eroare la gasire: " + e.getMessage());
        }
        return rezultat;
    }
}