package org.example.repositories.repo_db;

import org.example.domain.TypeDuck;
import org.example.domain.card.FlyingCard;
import org.example.domain.card.SwimmingCard;
import org.example.domain.card.TypeCard;
import org.example.domain.ducks.Duck;
import org.example.domain.ducks.FlyingDuck;
import org.example.domain.ducks.SwimmingDuck;
import org.example.utils.paging.Page;
import org.example.utils.paging.Pageable;

import java.sql.*;
import java.util.*;

public class RepoDBDuck implements RepoDB<Long, Duck> {

    private String url;
    private String username;
    private String password;

    public RepoDBDuck(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<Duck> findOne(Long id) {
        try(Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM duck WHERE id = ?")) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()){
                String name = result.getString("name");
                String email = result.getString("email");
                String password = result.getString("password");
                String tip = result.getString("tip");
                Double viteza = result.getDouble("viteza");
                Double rezistenta = result.getDouble("rezistenta");

                if (tip.equals("SWIMMING")){
                    SwimmingCard swimmingCard = new SwimmingCard(id, "SwimmingCard", List.of(), TypeCard.SWIMMING);
                    SwimmingDuck rata = new SwimmingDuck(id, name, email, password, TypeDuck.valueOf(tip), viteza, rezistenta,swimmingCard);
                    return Optional.of(rata);
                } else {
                    FlyingCard card = new FlyingCard(id, "FlyingCard", List.of(), TypeCard.FLYING);
                    FlyingDuck rata = new FlyingDuck(id, name, email, password, TypeDuck.valueOf(tip), viteza, rezistenta,card);
                    return Optional.of(rata);
                }
            }

            return Optional.empty();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Duck> findAll() {
        try(Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM duck")) {
            ResultSet result = statement.executeQuery();
            Set<Duck> ducks = new HashSet<>();

            while (result.next()){
                Long id = result.getLong("id");
                String name = result.getString("name");
                String email = result.getString("email");
                String password = result.getString("password");
                String tip = result.getString("tip");
                Double viteza = result.getDouble("viteza");
                Double rezistenta = result.getDouble("rezistenta");

                if (tip.equals("SWIMMING")){
                    SwimmingCard swimmingCard = new SwimmingCard(id, "SwimmingCard", List.of(), TypeCard.SWIMMING);
                    SwimmingDuck rata = new SwimmingDuck(id, name, email, password, TypeDuck.valueOf(tip), viteza, rezistenta,swimmingCard);
                    ducks.add(rata);
                } else {
                    FlyingCard card = new FlyingCard(id, "FlyingCard", List.of(), TypeCard.FLYING);
                    FlyingDuck rata = new FlyingDuck(id, name, email, password, TypeDuck.valueOf(tip), viteza, rezistenta,card);
                    ducks.add(rata);
                }
            }

            return ducks;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Duck> save(Duck entity) {
        String insertQuery = "INSERT INTO duck (name, email, password, tip, viteza, rezistenta) VALUES (?, ?, ?, ?, ?, ?)";
        try(Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
        PreparedStatement statement = connection.prepareStatement(insertQuery)){
            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getEmail());
            statement.setString(3, entity.getPassword());
            statement.setString(4, entity.getTip().toString());
            statement.setDouble(5, entity.getViteza());
            statement.setDouble(6, entity.getRezistenta());
            int response = statement.executeUpdate();
            return response == 0 ? Optional.of(entity) : Optional.empty();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Duck> delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        String deleteSQL = "DELETE FROM duck WHERE id = ?";
        try (var connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
            statement.setLong(1, id);
            Optional<Duck> foundUser = findOne(id);
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
    public Optional<Duck> update(Duck entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        String updateSQL = "UPDATE duck SET name = ?, email = ?, password = ?, tip = ?, viteza = ?, rezistenta = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(updateSQL);) {
            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getEmail());
            statement.setString(3, entity.getPassword());
            statement.setString(4, entity.getTip().toString());
            statement.setDouble(5, entity.getViteza());
            statement.setDouble(6, entity.getRezistenta());

            int response = statement.executeUpdate();
            return response == 0 ? Optional.of(entity) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Duck> findByUsername(String username) {
        String findQuery = "SELECT * FROM duck WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement statement = connection.prepareStatement(findQuery)) {

            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Long id = result.getLong("id");
                String name = result.getString("name");
                String email = result.getString("email");
                String password = result.getString("password");
                String tip = result.getString("tip");
                Double viteza = result.getDouble("viteza");
                Double rezistenta = result.getDouble("rezistenta");

                if ("SWIMMING".equalsIgnoreCase(tip)) {
                    SwimmingCard swimmingCard = new SwimmingCard(id, "SwimmingCard", List.of(), TypeCard.SWIMMING);
                    SwimmingDuck duck = new SwimmingDuck(id, name, email, password, TypeDuck.valueOf(tip), viteza, rezistenta, swimmingCard);
                    return Optional.of(duck);
                } else {
                    FlyingCard card = new FlyingCard(id, "FlyingCard", List.of(), TypeCard.FLYING);
                    FlyingDuck duck = new FlyingDuck(id, name, email, password, TypeDuck.valueOf(tip), viteza, rezistenta, card);
                    return Optional.of(duck);
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<Duck> findAllOnPage(Pageable pageable){
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            int totalNumberOfDucks = count(connection);
            List<Duck> moviesOnPage;
            if (totalNumberOfDucks > 0) {
                moviesOnPage = findAllOnPage(connection, pageable);
            } else {
                moviesOnPage = new ArrayList<>();
            }
            return new Page<>(moviesOnPage, totalNumberOfDucks);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Duck> findAllOnPage(Connection connection, Pageable pageable) throws SQLException {
        List<Duck> ducksOnPage = new ArrayList<>();
        String sql = "select * from duck limit ? offset ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pageable.getPageSize());
            statement.setInt(2, pageable.getPageSize() * pageable.getPageNumber());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    String tip = resultSet.getString("tip");
                    Double viteza = resultSet.getDouble("viteza");
                    Double rezistenta = resultSet.getDouble("rezistenta");

                    if ("SWIMMING".equalsIgnoreCase(tip)) {
                        SwimmingCard swimmingCard = new SwimmingCard(id, "SwimmingCard", List.of(), TypeCard.SWIMMING);
                        SwimmingDuck duck = new SwimmingDuck(id, name, email, password, TypeDuck.valueOf(tip), viteza, rezistenta, swimmingCard);
                        ducksOnPage.add(duck);
                    } else {
                        FlyingCard card = new FlyingCard(id, "FlyingCard", List.of(), TypeCard.FLYING);
                        FlyingDuck duck = new FlyingDuck(id, name, email, password, TypeDuck.valueOf(tip), viteza, rezistenta, card);
                        ducksOnPage.add(duck);
                    }
                }
            }
        }
        return ducksOnPage;
    }

    private int count(Connection connection) throws SQLException {
        String sql = "select count(*) as count from duck";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            int totalNumberOfDucks = 0;
            if (result.next()) {
                totalNumberOfDucks = result.getInt("count");
            }
            return totalNumberOfDucks;
        }
    }
}
