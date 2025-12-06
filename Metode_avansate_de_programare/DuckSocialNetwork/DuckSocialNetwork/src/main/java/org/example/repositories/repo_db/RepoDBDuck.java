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

    private Duck extractDuckFromResultSet(ResultSet result) throws SQLException {
        Long id = result.getLong("id");
        String name = result.getString("username");
        String email = result.getString("email");
        String password = result.getString("password");

        String tipStr = result.getString("tip");
        Double viteza = result.getDouble("viteza");
        Double rezistenta = result.getDouble("rezistenta");

        TypeDuck tip = TypeDuck.valueOf(tipStr);

        if (tip == TypeDuck.SWIMMING) {
            SwimmingCard swimmingCard = new SwimmingCard(id, "SwimmingCard", List.of(), TypeCard.SWIMMING);
            return new SwimmingDuck(id, name, email, password, tip, viteza, rezistenta, swimmingCard);
        } else {
            FlyingCard card = new FlyingCard(id, "FlyingCard", List.of(), TypeCard.FLYING);
            return new FlyingDuck(id, name, email, password, tip, viteza, rezistenta, card);
        }
    }

    @Override
    public Optional<Duck> findOne(Long id) {
        String sql = "SELECT u.id, u.username, u.email, u.password, d.tip, d.viteza, d.rezistenta " +
                "FROM users u " +
                "JOIN duck d ON u.id = d.id " +
                "WHERE u.id = ?";

        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return Optional.of(extractDuckFromResultSet(result));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Duck> findAll() {
        String sql = "SELECT u.id, u.username, u.email, u.password, d.tip, d.viteza, d.rezistenta " +
                "FROM users u " +
                "JOIN duck d ON u.id = d.id";

        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            List<Duck> ducks = new ArrayList<>();
            while (result.next()) {
                ducks.add(extractDuckFromResultSet(result));
            }
            return ducks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Duck> save(Duck entity) {
        String insertUserSql = "INSERT INTO users (username, email, password, user_type) VALUES (?, ?, ?, 'DUCK')";
        String insertDuckSql = "INSERT INTO duck (id, tip, viteza, rezistenta) VALUES (?, ?, ?, ?)";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(this.url, this.username, this.password);
            connection.setAutoCommit(false); // Pornim tranzactia manual

            Long generatedId = null;
            try (PreparedStatement stmtUser = connection.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS)) {
                stmtUser.setString(1, entity.getUsername());
                stmtUser.setString(2, entity.getEmail());
                stmtUser.setString(3, entity.getPassword());

                int affectedRows = stmtUser.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try (ResultSet generatedKeys = stmtUser.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getLong(1);
                        entity.setId(generatedId);
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }

            try (PreparedStatement stmtDuck = connection.prepareStatement(insertDuckSql)) {
                stmtDuck.setLong(1, generatedId);
                stmtDuck.setString(2, entity.getTip().toString());
                stmtDuck.setDouble(3, entity.getViteza());
                stmtDuck.setDouble(4, entity.getRezistenta());
                stmtDuck.executeUpdate();
            }

            connection.commit();
            return Optional.of(entity);

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Optional<Duck> delete(Long id) {
        if (id == null) throw new IllegalArgumentException("Id cannot be null");

        Optional<Duck> duckToDelete = findOne(id);
        if (duckToDelete.isEmpty()) return Optional.empty();

        String deleteSQL = "DELETE FROM users WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(deleteSQL)) {

            statement.setLong(1, id);
            statement.executeUpdate();

            return duckToDelete;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Duck> update(Duck entity) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");

        String updateUserSql = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";
        String updateDuckSql = "UPDATE duck SET tip = ?, viteza = ?, rezistenta = ? WHERE id = ?";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(this.url, this.username, this.password);
            connection.setAutoCommit(false);

            try (PreparedStatement stmtUser = connection.prepareStatement(updateUserSql)) {
                stmtUser.setString(1, entity.getUsername());
                stmtUser.setString(2, entity.getEmail());
                stmtUser.setString(3, entity.getPassword());
                stmtUser.setLong(4, entity.getId());
                stmtUser.executeUpdate();
            }

            try (PreparedStatement stmtDuck = connection.prepareStatement(updateDuckSql)) {
                stmtDuck.setString(1, entity.getTip().toString());
                stmtDuck.setDouble(2, entity.getViteza());
                stmtDuck.setDouble(3, entity.getRezistenta());
                stmtDuck.setLong(4, entity.getId());
                stmtDuck.executeUpdate();
            }

            connection.commit();
            return Optional.of(entity);

        } catch (SQLException e) {
            if (connection != null) {
                try { connection.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    @Override
    public Optional<Duck> findByUsername(String username) {
        String sql = "SELECT u.id, u.username, u.email, u.password, d.tip, d.viteza, d.rezistenta " +
                "FROM users u " +
                "JOIN duck d ON u.id = d.id " +
                "WHERE u.username = ?";

        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return Optional.of(extractDuckFromResultSet(result));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Iterable<Duck> findByType(TypeDuck type) {
        String sql = "SELECT u.id, u.username, u.email, u.password, d.tip, d.viteza, d.rezistenta " +
                "FROM users u " +
                "JOIN duck d ON u.id = d.id " +
                "WHERE d.tip = ?";

        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, type.toString());
            ResultSet result = statement.executeQuery();

            List<Duck> ducks = new ArrayList<>();
            while (result.next()) {
                ducks.add(extractDuckFromResultSet(result));
            }
            return ducks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<Duck> findAllOnPage(Pageable pageable) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            int totalNumberOfDucks = count(connection);
            List<Duck> ducksOnPage;
            if (totalNumberOfDucks > 0) {
                ducksOnPage = findAllOnPage(connection, pageable);
            } else {
                ducksOnPage = new ArrayList<>();
            }
            return new Page<>(ducksOnPage, totalNumberOfDucks);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Duck> findAllOnPage(Connection connection, Pageable pageable) throws SQLException {
        List<Duck> ducksOnPage = new ArrayList<>();
        String sql = "SELECT u.id, u.username, u.email, u.password, d.tip, d.viteza, d.rezistenta " +
                "FROM users u " +
                "JOIN duck d ON u.id = d.id " +
                "LIMIT ? OFFSET ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pageable.getPageSize());
            statement.setInt(2, pageable.getPageSize() * pageable.getPageNumber());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ducksOnPage.add(extractDuckFromResultSet(resultSet));
                }
            }
        }
        return ducksOnPage;
    }

    private int count(Connection connection) throws SQLException {
        String sql = "SELECT count(*) as count FROM duck";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            if (result.next()) {
                return result.getInt("count");
            }
            return 0;
        }
    }
}