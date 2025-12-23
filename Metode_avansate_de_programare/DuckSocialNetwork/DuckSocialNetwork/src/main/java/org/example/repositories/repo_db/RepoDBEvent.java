package org.example.repositories.repo_db;

import org.example.domain.TypeDuck;
import org.example.domain.ducks.Duck;
import org.example.domain.ducks.SwimmingDuck;
import org.example.domain.events.RaceEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepoDBEvent {

    private String url;
    private String username;
    private String password;

    public RepoDBEvent(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private List<SwimmingDuck> loadParticipants(Long eventId, Connection connection) {
        List<SwimmingDuck> participants = new ArrayList<>();

        // --- CORECTARE: Facem JOIN intre 'ducks', 'users' si tabela de legatura ---
        // Astfel aducem si 'username' din users, si 'viteza' din ducks.
        String sql = "SELECT u.username, u.email, u.password, d.id, d.tip, d.viteza, d.rezistenta " +
                "FROM duck d " +
                "INNER JOIN users u ON d.id = u.id " +
                "INNER JOIN race_event_duck r ON d.id = r.duck_id " +
                "WHERE r.event_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, eventId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Long id = rs.getLong("id");
                    String user = rs.getString("username");
                    String email = rs.getString("email");
                    String pass = rs.getString("password");

                    // Verificăm tipul (cu protectie la null)
                    String tipString = rs.getString("tip");
                    TypeDuck tip = (tipString != null) ? TypeDuck.valueOf(tipString) : TypeDuck.SWIMMING;

                    double viteza = rs.getDouble("viteza");
                    double rezistenta = rs.getDouble("rezistenta");

                    // Instanțiem SwimmingDuck
                    SwimmingDuck duck = new SwimmingDuck(id, user, email, pass, tip, viteza, rezistenta, null);

                    participants.add(duck);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participants;
    }

    public Iterable<RaceEvent> findAll() {
        List<RaceEvent> events = new ArrayList<>();
        String sql = "SELECT * FROM race_event";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String message = resultSet.getString("message");

                RaceEvent event = new RaceEvent(id, name);
                event.setMessage(message);

                List<SwimmingDuck> participants = loadParticipants(id, connection);
                event.setDucks_final(participants);

                events.add(event);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return events;
    }

    public Optional<RaceEvent> save(RaceEvent entity) {
        String sql = "INSERT INTO race_event (name, message) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entity.getName());
            ps.setString(2, entity.getMessage());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                entity.setId(rs.getLong(1));
                return Optional.of(entity);
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    public Optional<RaceEvent> update(RaceEvent entity) {
        String updateEventSql = "UPDATE race_event SET name = ?, message = ? WHERE id = ?";
        String insertParticipantSql = "INSERT INTO race_event_duck (event_id, duck_id) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            PreparedStatement ps = connection.prepareStatement(updateEventSql);
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getMessage());
            ps.setLong(3, entity.getId());

            int affected = ps.executeUpdate();

            if (affected > 0) {
                try (PreparedStatement psPart = connection.prepareStatement(insertParticipantSql)) {
                    for (Duck d : entity.getDucks_final()) {
                        psPart.setLong(1, entity.getId());
                        psPart.setLong(2, d.getId());

                        try {
                            psPart.executeUpdate();
                        } catch (SQLException e) {
                            // Ignorăm duplicatele (dacă rața e deja înscrisă)
                        }
                    }
                }
                return Optional.of(entity);
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}