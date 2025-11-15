package org.example.repositories.repo_db;

import org.example.domain.Entity;
import org.example.domain.Friendship;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class RepoDBFriendship implements RepoDB<Long, Friendship>{

    private String url;
    private String username;
    private String password;

    public RepoDBFriendship(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<Friendship> findOne(Long id) {
        try(Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM friendships WHERE id = ?")) {

            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if(result.next()){
                String first_name = result.getString("first_name");
                String second_name = result.getString("second_name");

                Friendship friendship = new Friendship(id, first_name, second_name);
                return Optional.of(friendship);
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Friendship> findAll() {
        try(Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM friendships")){
            ResultSet resultSet = statement.executeQuery();
            Set<Friendship> friendships = new HashSet<>();

            while(resultSet.next()){
                Long id = resultSet.getLong("id");
                String first_name = resultSet.getString("first_name");
                String second_name = resultSet.getString("second_name");

                Friendship friendship = new Friendship(id, first_name, second_name);
                friendships.add(friendship);
            }

            return friendships;

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Friendship> save(Friendship entity) {
        String insertQuery = "INSERT INTO friendships (first_name, second_name) VALUES (?, ?)";
        try(Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
        PreparedStatement statement = connection.prepareStatement(insertQuery)){

            statement.setString(1, entity.getFirst_friend_username());
            statement.setString(2, entity.getSecond_friend_username());
            int response = statement.executeUpdate();
            return response == 0 ? Optional.of(entity) : Optional.empty();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Friendship> delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        String deleteSQL = "DELETE FROM friendships WHERE id = ?";
        try (var connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
            statement.setLong(1, id);
            Optional<Friendship> foundFriendship = findOne(id);
            int response = 0;
            if (foundFriendship.isPresent()) {
                response = statement.executeUpdate();
            }
            return response == 0 ? Optional.empty() : foundFriendship;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Friendship> update(Friendship entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        String updateSQL = "UPDATE friendships SET first_name = ?, second_name = ? WHERE id =";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(updateSQL);) {
            statement.setString(1, entity.getFirst_friend_username());
            statement.setString(2, entity.getSecond_friend_username());
            statement.setLong(3, entity.getId());

            int response = statement.executeUpdate();
            return response == 0 ? Optional.of(entity) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Friendship> findByUsername(String username) {
        String findQuery = "SELECT * FROM friendships WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement statement = connection.prepareStatement(findQuery)) {

            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Long id = result.getLong("id");
                String first_name = result.getString("first_name");
                String second_name = result.getString("second_name");
                Friendship friendship = new Friendship(id, first_name, second_name);
                return Optional.of(friendship);
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<Friendship> findAllOnPage(Pageable pageable) {
        return null;
    }
}
