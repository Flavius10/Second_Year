package grup.repo;

import grup.domain.Drone;
import grup.domain.Producator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepoProducator {

    // Metoda pentru a obtine toti producatorii
    public List<Producator> getProducatori(){
        String sql = "SELECT * FROM Producator";
        List<Producator> producatoriList = new ArrayList<>();

        try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Producator producator = new Producator(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getString("tara_origine")
                );
                producatoriList.add(producator);
            }

        } catch (java.sql.SQLException e){
            System.err.println("Eroare la obținerea producătorilor: " + e.getMessage());
        }

        return producatoriList;
    }

}
