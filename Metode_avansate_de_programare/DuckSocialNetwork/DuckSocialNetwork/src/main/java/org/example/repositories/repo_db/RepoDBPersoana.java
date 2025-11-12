package org.example.repositories.repo_db;

import org.example.domain.Persoana;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

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
        return null;
    }

    @Override
    public Optional<Persoana> save(Persoana entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Persoana> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Persoana> update(Persoana entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Persoana> findByUsername(String username) {
        return Optional.empty();
    }
}
