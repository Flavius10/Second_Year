package org.example.repositories.repo_db;

import org.example.domain.Persoana;
import org.example.domain.ducks.Duck;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class RepoDBPersoana implements RepoDB<Long, Persoana>{

    private String url;
    private String username;
    private String password;

    public RepoDBPersoana(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }


    @Override
    public Optional<Persoana> findOne(Long id) {

        try(Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM persoana WHERE id = ?")
        ) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()){
                String username = result.getString("username");
                String email = result.getString("email");
                String password = result.getString("password");
                String nume = result.getString("nume");
                String prenume = result.getString("prenume");
                String ocupatie = result.getString("ocupatie");
                LocalDate dataNastere = result.getDate("data_nastere").toLocalDate();

                Persoana persoana = new Persoana(id, username, email, password, nume, prenume, ocupatie, dataNastere);
                return Optional.of(persoana);
            }

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Persoana> findAll() {

        try(
                Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM persoana")
                ){
            ResultSet result = statement.executeQuery();
            Set<Persoana> persoane = new HashSet<>();
            while(result.next()){
                Long id = result.getLong("id");
                String username = result.getString("username");
                String email = result.getString("email");
                String password = result.getString("password");
                String nume = result.getString("nume");
                String prenume = result.getString("prenume");
                String ocupatie = result.getString("ocupatie");
                LocalDate dataNastere = result.getDate("data_nastere").toLocalDate();

                Persoana persoana = new Persoana(id, username, email, password, nume, prenume, ocupatie, dataNastere);
                persoane.add(persoana);
            }
            return persoane;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Persoana> save(Persoana entity) {
        String insertQuery = "INSERT INTO persoana (username, email, password, nume, prenume, ocupatie, data_nastere) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(
                Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
                PreparedStatement statement = connection.prepareStatement(insertQuery);
                ){
            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getEmail());
            statement.setString(3, entity.getPassword());
            statement.setString(4, entity.getNume());
            statement.setString(5, entity.getPrenume());
            statement.setString(6, entity.getOcupatie());
            statement.setDate(7, Date.valueOf(entity.getDataNastere()));
            int response = statement.executeUpdate();
            return response == 0 ? Optional.of(entity) : Optional.empty();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Persoana> delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        String deleteSQL = "DELETE FROM persoana WHERE id = ?";
        try (var connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
            statement.setLong(1, id);
            Optional<Persoana> foundUser = findOne(id);
            int response = 0;
            if (foundUser.isPresent()) {
                response = statement.executeUpdate();
            }
            return response == 0 ? Optional.empty() : foundUser;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Persoana> update(Persoana entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        String updateSQL = "UPDATE persoana SET username = ?, email = ?, password = ?, nume = ?, prenume = ?, ocupatie = ?, data_nastere = ? WHERE id = ?";
        try (var connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(updateSQL);) {
            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getEmail());
            statement.setString(3, entity.getPassword());
            statement.setString(4, entity.getNume());
            statement.setString(5, entity.getPrenume());
            statement.setString(6, entity.getOcupatie());
            statement.setDate(7, Date.valueOf(entity.getDataNastere()));

            int response = statement.executeUpdate();
            return response == 0 ? Optional.of(entity) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Persoana> findByUsername(String username) {
       if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
       }

       String findByUsernameQuery = "SELECT * FROM persoana WHERE username = ?";
       try(
               Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
               PreparedStatement statement = connection.prepareStatement(findByUsernameQuery);
               ){
           statement.setString(1, username);
           ResultSet result = statement.executeQuery();

           if (result.next()){
               Long id = result.getLong("id");
               String email = result.getString("email");
               String password = result.getString("password");
               String nume = result.getString("nume");
               String prenume = result.getString("prenume");
               String ocupatie = result.getString("ocupatie");
               LocalDate dataNastere = result.getDate("data_nastere").toLocalDate();

               Persoana persoana = new Persoana(id, username, email, password, nume, prenume, ocupatie, dataNastere);
               return Optional.of(persoana);
           }

            return Optional.empty();
       } catch(SQLException e){
           throw new RuntimeException(e);
       }
    }
}
