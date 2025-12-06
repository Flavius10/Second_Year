package org.example.repositories.repo_db;

import org.example.domain.Friendship;
import org.example.utils.paging.Page;
import org.example.utils.paging.Pageable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepoDBFriendship implements RepoDB<Long, Friendship> {

    private String url;
    private String username;
    private String password;

    public RepoDBFriendship(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<Friendship> findOne(Long id) {
        String sql = "SELECT * FROM friendships WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String u1 = result.getString("user1_username");
                String u2 = result.getString("user2_username");
                return Optional.of(new Friendship(id, u1, u2));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Friendship> findAll() {
        String sql = "SELECT * FROM friendships";
        List<Friendship> friendships = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                Long id = result.getLong("id");
                String u1 = result.getString("user1_username");
                String u2 = result.getString("user2_username");
                friendships.add(new Friendship(id, u1, u2));
            }
            return friendships;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Friendship> save(Friendship entity) {
        // Inseram doar numele userilor. ID-ul e serial, data nu mai exista.
        String sql = "INSERT INTO friendships (user1_username, user2_username) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, entity.getFirst_friend_username());
            statement.setString(2, entity.getSecond_friend_username());

            int response = statement.executeUpdate();
            return response == 0 ? Optional.empty() : Optional.of(entity);

        } catch (SQLException e) {
            throw new RuntimeException("Eroare la save: Probabil userii nu exista in tabela users.", e);
        }
    }

    @Override
    public Optional<Friendship> delete(Long id) {
        if (id == null) throw new IllegalArgumentException("Id cannot be null");

        Optional<Friendship> found = findOne(id);
        if (found.isEmpty()) return Optional.empty();

        String sql = "DELETE FROM friendships WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
            return found;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Friendship> update(Friendship entity) {
        if (entity == null) throw new IllegalArgumentException("Entity null");

        String sql = "UPDATE friendships SET user1_username = ?, user2_username = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, entity.getFirst_friend_username());
            statement.setString(2, entity.getSecond_friend_username());
            statement.setLong(3, entity.getId());

            int response = statement.executeUpdate();
            return response == 0 ? Optional.empty() : Optional.of(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Friendship> findByUsername(String username) {
        String sql = "SELECT * FROM friendships WHERE user1_username = ? OR user2_username = ?";

        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, username);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Long id = result.getLong("id");
                String u1 = result.getString("user1_username");
                String u2 = result.getString("user2_username");
                return Optional.of(new Friendship(id, u1, u2));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<Friendship> findAllOnPage(Pageable pageable) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            int total = count(connection);
            List<Friendship> pageList = new ArrayList<>();

            if (total > 0) {
                String sql = "SELECT * FROM friendships LIMIT ? OFFSET ?";
                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, pageable.getPageSize());
                    stmt.setInt(2, pageable.getPageSize() * pageable.getPageNumber());

                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        Long id = rs.getLong("id");
                        String u1 = rs.getString("user1_username");
                        String u2 = rs.getString("user2_username");
                        pageList.add(new Friendship(id, u1, u2));
                    }
                }
            }
            return new Page<>(pageList, total);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int count(Connection connection) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT count(*) as cnt FROM friendships");
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt("cnt") : 0;
        }
    }
}