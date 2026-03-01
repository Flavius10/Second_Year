package grup.repo;

import grup.domain.Drone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepoDrone {

    // Metoda care returneaza toate dronele unui producator dupa id-ul sau
    public List<Drone> getDronesByProducator(int producatorId)  {
        List<Drone> dronesList = new ArrayList<>();
        String sql = "SELECT id, nume_model, greutate_grame, tip_cadru, producator_id FROM Drona WHERE producator_id = ?";

        try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1, producatorId);

            try(ResultSet rs = preparedStatement.executeQuery()){
                while(rs.next()){
                   Drone drone = new Drone(
                           rs.getInt("id"),
                           rs.getString("nume_model"),
                           rs.getInt("greutate_grame"),
                           rs.getString("tip_cadru"),
                           rs.getInt("producator_id")
                   );
                    dronesList.add(drone);
                }
            }

        } catch (SQLException e){
            System.err.println("Eroare la obținerea dronelor: " + e.getMessage());
        }

        return dronesList;
    }

    // Metoda care returneaza toate dronele din baza de date
    public List<Drone> getAllDrones() {
        List<Drone> dronesList = new ArrayList<>();
        String sql = "SELECT * FROM Drona";

        try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery()){

            while(rs.next()){
                Drone drone = new Drone(
                        rs.getInt("id"),
                        rs.getString("nume_model"),
                        rs.getInt("greutate_grame"),
                        rs.getString("tip_cadru"),
                        rs.getInt("producator_id")
                );
                dronesList.add(drone);
            }

        } catch (SQLException e){
            System.err.println("Eroare la obținerea dronelor: " + e.getMessage());
        }

        return dronesList;
    }

    // Metoda care adauga o drona in baza de date
    public void addDrone(Drone drone){
        String sql = "INSERT INTO Drona (nume_model, greutate_grame, tip_cadru, producator_id) VALUES (?, ?, ?, ?)";

        try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, drone.getNume_model());
            preparedStatement.setInt(2, drone.getGreutate_grame());
            preparedStatement.setString(3, drone.getTip_cadru());
            preparedStatement.setInt(4, drone.getProducator_id());

            preparedStatement.executeUpdate();

        } catch (SQLException e){
            System.err.println("Eroare la adăugarea dronei: " + e.getMessage());
        }
    }

    // Metoda care actualizeaza o drona in baza de date
    public void updateDrone(Drone drone){
        String sql = "UPDATE Drona SET nume_model = ?, greutate_grame = ?, tip_cadru = ?, producator_id = ? WHERE id = ?";

        try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, drone.getNume_model());
            preparedStatement.setInt(2, drone.getGreutate_grame());
            preparedStatement.setString(3, drone.getTip_cadru());
            preparedStatement.setInt(4, drone.getProducator_id());
            preparedStatement.setInt(5, drone.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e){
            System.err.println("Eroare la actualizarea dronei: " + e.getMessage());
        }
    }

    // Metoda care sterge o drona din baza de date dupa id-ul sau
    public void deleteDrone(int droneId){
        String sql = "DELETE FROM Drona WHERE id = ?";

        try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1, droneId);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            System.err.println("Eroare la ștergerea dronei: " + e.getMessage());
        }
    }
}
