package org.example.repositories.repo_db;

import org.example.domain.Persoana;
import org.example.utils.paging.Page;
import org.example.utils.paging.Pageable;

import java.sql.*;
import java.sql.Date; // Atentie la importul asta pentru SQL Date
import java.time.LocalDate;
import java.util.*;

public class RepoDBPersoana implements RepoDB<Long, Persoana> {

    private String url;
    private String username;
    private String password;

    public RepoDBPersoana(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    // Metoda ajutatoare pentru a evita duplicarea codului de extragere
    private Persoana extractPersoanaFromResultSet(ResultSet result) throws SQLException {
        Long id = result.getLong("id");
        // Date din tabelul USERS
        String username = result.getString("username");
        String email = result.getString("email");
        String password = result.getString("password");

        // Date din tabelul PERSOANA
        String nume = result.getString("nume");
        String prenume = result.getString("prenume");
        String ocupatie = result.getString("ocupatie");

        Date dateSql = result.getDate("data_nastere");
        LocalDate dataNastere = (dateSql != null) ? dateSql.toLocalDate() : null;

        return new Persoana(id, username, email, password, nume, prenume, ocupatie, dataNastere);
    }

    @Override
    public Optional<Persoana> findOne(Long id) {
        // JOIN intre users si persoana
        String sql = "SELECT u.id, u.username, u.email, u.password, " +
                "p.nume, p.prenume, p.ocupatie, p.data_nastere " +
                "FROM users u " +
                "JOIN persoana p ON u.id = p.id " +
                "WHERE u.id = ?";

        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return Optional.of(extractPersoanaFromResultSet(result));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Persoana> findAll() {
        String sql = "SELECT u.id, u.username, u.email, u.password, " +
                "p.nume, p.prenume, p.ocupatie, p.data_nastere " +
                "FROM users u " +
                "JOIN persoana p ON u.id = p.id";

        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            List<Persoana> persoane = new ArrayList<>();
            while (result.next()) {
                persoane.add(extractPersoanaFromResultSet(result));
            }
            return persoane;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Persoana> save(Persoana entity) {
        String insertUserSql = "INSERT INTO users (username, email, password, user_type) VALUES (?, ?, ?, 'PERSOANA')";
        String insertPersoanaSql = "INSERT INTO persoana (id, nume, prenume, ocupatie, data_nastere) VALUES (?, ?, ?, ?, ?)";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(this.url, this.username, this.password);
            connection.setAutoCommit(false);

            Long generatedId = null;
            try (PreparedStatement stmtUser = connection.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS)) {
                stmtUser.setString(1, entity.getUsername());
                stmtUser.setString(2, entity.getEmail());
                stmtUser.setString(3, entity.getPassword());

                int affectedRows = stmtUser.executeUpdate();
                if (affectedRows == 0) throw new SQLException("Creating user failed, no rows affected.");

                try (ResultSet generatedKeys = stmtUser.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getLong(1);
                        entity.setId(generatedId);
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }

            try (PreparedStatement stmtPersoana = connection.prepareStatement(insertPersoanaSql)) {
                stmtPersoana.setLong(1, generatedId);
                stmtPersoana.setString(2, entity.getNume());
                stmtPersoana.setString(3, entity.getPrenume());
                stmtPersoana.setString(4, entity.getOcupatie());
                stmtPersoana.setDate(5, Date.valueOf(entity.getDataNastere()));

                stmtPersoana.executeUpdate();
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
                try { connection.setAutoCommit(true); connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    @Override
    public Optional<Persoana> delete(Long id) {
        if (id == null) throw new IllegalArgumentException("Id cannot be null");

        // Cautam intai persoana ca sa o returnam
        Optional<Persoana> found = findOne(id);
        if (found.isEmpty()) return Optional.empty();

        String deleteSQL = "DELETE FROM users WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(deleteSQL)) {

            statement.setLong(1, id);
            statement.executeUpdate();
            return found;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Persoana> update(Persoana entity) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");

        String updateUserSql = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";
        String updatePersoanaSql = "UPDATE persoana SET nume = ?, prenume = ?, ocupatie = ?, data_nastere = ? WHERE id = ?";

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

            try (PreparedStatement stmtPersoana = connection.prepareStatement(updatePersoanaSql)) {
                stmtPersoana.setString(1, entity.getNume());
                stmtPersoana.setString(2, entity.getPrenume());
                stmtPersoana.setString(3, entity.getOcupatie());
                stmtPersoana.setDate(4, Date.valueOf(entity.getDataNastere()));
                stmtPersoana.setLong(5, entity.getId());
                stmtPersoana.executeUpdate();
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
    public Optional<Persoana> findByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        String sql = "SELECT u.id, u.username, u.email, u.password, " +
                "p.nume, p.prenume, p.ocupatie, p.data_nastere " +
                "FROM users u " +
                "JOIN persoana p ON u.id = p.id " +
                "WHERE u.username = ?";

        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return Optional.of(extractPersoanaFromResultSet(result));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<Persoana> findAllOnPage(Pageable pageable) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            int total = count(connection);
            List<Persoana> onPage;
            if (total > 0) {
                onPage = findAllOnPage(connection, pageable);
            } else {
                onPage = new ArrayList<>();
            }
            return new Page<>(onPage, total);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Persoana> findAllOnPage(Connection connection, Pageable pageable) throws SQLException {
        List<Persoana> list = new ArrayList<>();

        String sql = "SELECT u.id, u.username, u.email, u.password, " +
                "p.nume, p.prenume, p.ocupatie, p.data_nastere " +
                "FROM users u " +
                "JOIN persoana p ON u.id = p.id " +
                "LIMIT ? OFFSET ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pageable.getPageSize());
            statement.setInt(2, pageable.getPageSize() * pageable.getPageNumber());

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    list.add(extractPersoanaFromResultSet(result));
                }
            }
        }
        return list;
    }

    private int count(Connection connection) throws SQLException {

        String sql = "SELECT count(*) as count FROM persoana";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            if (result.next()) {
                return result.getInt("count");
            }
            return 0;
        }
    }
}