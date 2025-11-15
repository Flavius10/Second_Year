package org.example.repositories.repo_db;

import org.example.domain.ducks.SwimmingDuck;
import org.example.domain.events.RaceEvent;
import org.example.utils.paging.Page;
import org.example.utils.paging.Pageable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepoDBEvent implements RepoDB<Long, RaceEvent> {

    private final String url;
    private final String username;
    private final String password;

    public RepoDBEvent(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<RaceEvent> save(RaceEvent event) {
        String insertEventSQL = "INSERT INTO race_event (name, message) VALUES (?, ?)";
        String insertDuckSQL = "INSERT INTO race_event_duck (event_id, duck_id) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            connection.setAutoCommit(false);

            try (PreparedStatement psEvent = connection.prepareStatement(insertEventSQL, Statement.RETURN_GENERATED_KEYS)) {
                psEvent.setString(1, event.getName());
                psEvent.setString(2, event.getMessage() != null ? event.getMessage() : "");
                int affectedRows = psEvent.executeUpdate();

                if (affectedRows == 0) {
                    connection.rollback();
                    return Optional.of(event);
                }

                try (ResultSet rs = psEvent.getGeneratedKeys()) {
                    if (rs.next()) event.setId(rs.getLong(1));
                }
            }

            if (event.getDucks_final() != null && !event.getDucks_final().isEmpty()) {
                try (PreparedStatement psDuck = connection.prepareStatement(insertDuckSQL)) {
                    for (SwimmingDuck duck : event.getDucks_final()) {
                        psDuck.setLong(1, event.getId());
                        psDuck.setLong(2, duck.getId());
                        psDuck.addBatch();
                    }
                    psDuck.executeBatch();
                }
            }

            connection.commit();
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<RaceEvent> findOne(Long id) {
        String selectEvent = "SELECT * FROM race_event WHERE id = ?";
        String selectDucks = "SELECT duck.* FROM duck " +
                "JOIN race_event_duck red ON duck.id = red.duck_id " +
                "WHERE red.event_id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            RaceEvent event = null;

            try (PreparedStatement psEvent = connection.prepareStatement(selectEvent)) {
                psEvent.setLong(1, id);
                ResultSet rsEvent = psEvent.executeQuery();
                if (rsEvent.next()) {
                    String name = rsEvent.getString("name");
                    String message = rsEvent.getString("message");
                    event = new RaceEvent(id, name);
                    event.setMessage(message);
                }
            }

            if (event != null) {
                List<SwimmingDuck> ducks = new ArrayList<>();
                try (PreparedStatement psDucks = connection.prepareStatement(selectDucks)) {
                    psDucks.setLong(1, id);
                    ResultSet rsDucks = psDucks.executeQuery();
                    while (rsDucks.next()) {
                        Long duckId = rsDucks.getLong("id");
                        String username = rsDucks.getString("name");
                        double viteza = rsDucks.getDouble("viteza");
                        double rezistenta = rsDucks.getDouble("rezistenta");

                        SwimmingDuck duck = new SwimmingDuck(duckId, username, "", "", null, viteza, rezistenta, null);
                        ducks.add(duck);
                    }
                }
                event.setDucks_final(ducks);
            }

            return Optional.ofNullable(event);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<RaceEvent> findAll() {
        String selectAll = "SELECT * FROM race_event";
        List<RaceEvent> events = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(selectAll);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String message = rs.getString("message");
                RaceEvent event = new RaceEvent(id, name);
                event.setMessage(message);
                events.add(event);
            }
            return events;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<RaceEvent> update(RaceEvent entity) {
        String updateEvent = "UPDATE race_event SET name = ?, message = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(updateEvent)) {

            ps.setString(1, entity.getName());
            ps.setString(2, entity.getMessage());
            ps.setLong(3, entity.getId());
            int affected = ps.executeUpdate();
            return affected == 0 ? Optional.of(entity) : Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<RaceEvent> delete(Long id) {
        String deleteDucks = "DELETE FROM race_event_duck WHERE event_id = ?";
        String deleteEvent = "DELETE FROM race_event WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            connection.setAutoCommit(false);

            try (PreparedStatement psDucks = connection.prepareStatement(deleteDucks)) {
                psDucks.setLong(1, id);
                psDucks.executeUpdate();
            }

            int affected;
            try (PreparedStatement psEvent = connection.prepareStatement(deleteEvent)) {
                psEvent.setLong(1, id);
                affected = psEvent.executeUpdate();
            }

            connection.commit();
            return affected == 0 ? Optional.empty() : Optional.of(new RaceEvent(id, null));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<RaceEvent> findByUsername(String username) {
        // Nu are sens pentru RaceEvent
        return Optional.empty();
    }

    @Override
    public Page<RaceEvent> findAllOnPage(Pageable pageable) {
        String countSQL = "SELECT COUNT(*) as count FROM race_event";
        String selectSQL = "SELECT * FROM race_event LIMIT ? OFFSET ?";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            int total = 0;
            try (PreparedStatement psCount = connection.prepareStatement(countSQL);
                 ResultSet rsCount = psCount.executeQuery()) {
                if (rsCount.next()) total = rsCount.getInt("count");
            }

            List<RaceEvent> events = new ArrayList<>();
            try (PreparedStatement ps = connection.prepareStatement(selectSQL)) {
                ps.setInt(1, pageable.getPageSize());
                ps.setInt(2, pageable.getPageSize() * pageable.getPageNumber());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Long id = rs.getLong("id");
                    String name = rs.getString("name");
                    String message = rs.getString("message");
                    RaceEvent event = new RaceEvent(id, name);
                    event.setMessage(message);
                    events.add(event);
                }
            }

            return new Page<>(events, total);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
