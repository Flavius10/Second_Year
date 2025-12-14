package org.example.repositories.repo_db;

import org.example.domain.friendship.Request;
import org.example.utils.paging.Page;
import org.example.utils.paging.Pageable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepoDBRequest implements RepoDB<Long, Request>{

    private String url;
    private String username;
    private String password;

    public RepoDBRequest(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Request extractRequestFromResultSet(ResultSet result) throws SQLException {
        Long id = result.getLong("id");
        String senderUsername = result.getString("sender");
        String receiverUsername = result.getString("receiver");
        String status = result.getString("status");

        return new Request(id, senderUsername, receiverUsername, status);
    }

    @Override
    public Optional<Request> findOne(Long aLong) {
        String sql = "SELECT * FROM request WHERE id = ?";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, aLong);
            ResultSet result = statement.executeQuery();

            if (result.next()){
                return Optional.of(extractRequestFromResultSet(result));
            }

            return Optional.empty();

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Request> findAll() {
        String sql = "SELECT * FROM request";

        try(Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
        PreparedStatement statement = connection.prepareStatement(sql)){

            ResultSet resultSet = statement.executeQuery();

            List<Request> request = new ArrayList<>();

            while(resultSet.next()){
                request.add(extractRequestFromResultSet(resultSet));
            }

            return request;

        } catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<Request> save(Request entity) {
        String sql = "INSERT INTO request (sender, receiver, status) VALUES (?, ?, ?)";

        try(Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getSenderUsername());
            statement.setString(2, entity.getReceiverUsername());
            statement.setString(3, entity.getStatus());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating request failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating request failed, no ID obtained.");
                }
            }

            return Optional.empty();

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Request> delete(Long aLong) {
        if (aLong == null) throw new IllegalArgumentException("Id null");

        Optional<Request> requestToDelete = findOne(aLong);
        if (requestToDelete.isEmpty()) return Optional.empty();

        String deleteSql = "DELETE FROM request WHERE id = ?";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(deleteSql)) {

            statement.setLong(1, aLong);
            statement.executeUpdate();

            return requestToDelete;

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Request> update(Request entity) {
        String sql = "UPDATE request SET status = ? WHERE id = ?";

        try(Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, entity.getStatus());
            statement.setLong(2, entity.getId());
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) return Optional.empty();

            return Optional.of(entity);

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Iterable<Request> findByUsernameRequest(String username) {
        String sql = "SELECT * FROM request WHERE receiver = ?";

        List<Request> requests = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Request request = extractRequestFromResultSet(result);
                requests.add(request);
            }
            return requests;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Request> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Page<Request> findAllOnPage(Pageable pageable) {
        return null;
    }
}
