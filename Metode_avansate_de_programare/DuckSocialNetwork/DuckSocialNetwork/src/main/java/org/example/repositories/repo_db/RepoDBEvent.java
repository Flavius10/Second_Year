package org.example.repositories.repo_db;

import org.example.domain.TypeDuck;
import org.example.domain.ducks.Duck;
import org.example.domain.ducks.Lane;
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

                    String tipString = rs.getString("tip");
                    TypeDuck tip = (tipString != null) ? TypeDuck.valueOf(tipString) : TypeDuck.SWIMMING;

                    double viteza = rs.getDouble("viteza");
                    double rezistenta = rs.getDouble("rezistenta");

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

                event.setDucks_final(loadParticipants(id, connection));

                event.setLanes(loadLanes(id, connection));

                events.add(event);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return events;
    }


    public Optional<RaceEvent> save(RaceEvent entity) {
        String insertEventSql = "INSERT INTO race_event (name, message) VALUES (?, ?)";
        String insertLaneSql = "INSERT INTO lanes (event_id, length) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            connection.setAutoCommit(false);

            try (PreparedStatement ps = connection.prepareStatement(insertEventSql, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, entity.getName());
                ps.setString(2, entity.getMessage());
                int affected = ps.executeUpdate();

                if (affected > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            Long eventId = rs.getLong(1);
                            entity.setId(eventId);

                            if (entity.getLanes() != null && !entity.getLanes().isEmpty()) {
                                try (PreparedStatement psLane = connection.prepareStatement(insertLaneSql)) {
                                    for (Lane lane : entity.getLanes()) {
                                        psLane.setLong(1, eventId);
                                        psLane.setDouble(2, lane.getLength());
                                        psLane.executeUpdate();
                                    }
                                }
                            }

                            connection.commit();
                            return Optional.of(entity);
                        }
                    }
                }
                connection.rollback();
                return Optional.empty();

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    private List<Lane> loadLanes(Long eventId, Connection connection) {
        List<Lane> lanes = new ArrayList<>();
        String sql = "SELECT * FROM lanes WHERE event_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Long id = rs.getLong("id");
                    int length = rs.getInt("length");

                    lanes.add(new Lane(id, length));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lanes;
    }
}