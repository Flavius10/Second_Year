package grup.repo;

import grup.domain.Car;
import grup.domain.CarStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepoDBCar {

    private String url;
    private String username;
    private String password;

    public RepoDBCar(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private Car extractEntity(ResultSet rs) throws SQLException {

        String denumire = rs.getString("denumire");
        String descriere = rs.getString("descriere");
        Integer pret = rs.getInt("pret");
        CarStatus status = CarStatus.valueOf(rs.getString("status"));
        String comentarii = rs.getString("comentariu");

        return new Car(denumire, descriere, pret, status, comentarii);
    }

    public void updateStatus(String username, String comentary, CarStatus status) {
        String sql = "UPDATE cars SET status = ?, comentariu = ? WHERE denumire = ?";
        try (Connection connection = DriverManager.getConnection(url, this.username, this.password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, status.toString());
            ps.setString(2, comentary);
            ps.setString(3, username);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Car> findAll() {
        String sql = "SELECT * FROM cars";
        List<Car> list = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(extractEntity(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Optional<Car> findOne(CarStatus status) {
        String sql = "SELECT * FROM cars WHERE status = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status.toString());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(extractEntity(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }


}
