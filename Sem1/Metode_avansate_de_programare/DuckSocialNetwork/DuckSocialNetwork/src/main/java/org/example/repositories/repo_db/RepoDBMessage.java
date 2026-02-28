package org.example.repositories.repo_db;

import org.example.domain.*;
import org.example.domain.card.FlyingCard;
import org.example.domain.card.SwimmingCard;
import org.example.domain.card.TypeCard;
import org.example.domain.ducks.FlyingDuck;
import org.example.domain.ducks.SwimmingDuck;
import org.example.domain.messaging.Message;
import org.example.domain.messaging.ReplyMessage;
import org.example.utils.paging.Page;
import org.example.utils.paging.Pageable;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepoDBMessage implements RepoDB<Long, Message> {

    private String url;
    private String username;
    private String password;

    public RepoDBMessage(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private User findUserById(Long id, Connection connection) throws SQLException {
        String sql = "SELECT u.id, u.username, u.email, u.password, u.user_type, " +
                "p.nume, p.prenume, p.ocupatie, p.data_nastere, " +
                "d.tip, d.viteza, d.rezistenta " +
                "FROM users u " +
                "LEFT JOIN persoana p ON u.id = p.id " +
                "LEFT JOIN duck d ON u.id = d.id " +
                "WHERE u.id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String userType = rs.getString("user_type");
                    String username = rs.getString("username");
                    String email = rs.getString("email");
                    String pass = rs.getString("password");

                    if ("PERSOANA".equalsIgnoreCase(userType)) {
                        String nume = rs.getString("nume");
                        String prenume = rs.getString("prenume");
                        String ocupatie = rs.getString("ocupatie");
                        Date dateSql = rs.getDate("data_nastere");
                        var dataNastere = (dateSql != null) ? dateSql.toLocalDate() : null;

                        return new Persoana(id, username, email, pass, nume, prenume, ocupatie, dataNastere);

                    } else if ("DUCK".equalsIgnoreCase(userType)) {
                        String tipDuck = rs.getString("tip");
                        Double viteza = rs.getDouble("viteza");
                        Double rezistenta = rs.getDouble("rezistenta");

                        if ("SWIMMING".equalsIgnoreCase(tipDuck)) {
                            SwimmingCard card = new SwimmingCard(id, "SwimCard", List.of(), TypeCard.SWIMMING);
                            return new SwimmingDuck(id, username, email, pass, TypeDuck.SWIMMING, viteza, rezistenta, card);
                        } else {
                            FlyingCard card = new FlyingCard(id, "FlyCard", List.of(), TypeCard.FLYING);
                            return new FlyingDuck(id, username, email, pass, TypeDuck.FLYING, viteza, rezistenta, card);
                        }
                    }
                }
            }
        }
        return null;
    }

    private List<User> getRecipients(Long messageId, Connection connection) throws SQLException {
        List<User> list = new ArrayList<>();
        String sql = "SELECT recipient_id FROM message_recipients WHERE message_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, messageId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Long userId = rs.getLong("recipient_id");
                    User user = findUserById(userId, connection);
                    if (user != null) {
                        list.add(user);
                    }
                }
            }
        }
        return list;
    }

    private Message internalFindOne(Long id, Connection connection) throws SQLException {
        String sql = "SELECT * FROM messages WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return extractMessage(result, connection);
                }
            }
        }
        return null;
    }

    private Message extractMessage(ResultSet rs, Connection connection) throws SQLException {
        Long id = rs.getLong("id");
        Long senderId = rs.getLong("sender_id");
        String text = rs.getString("text");
        Timestamp ts = rs.getTimestamp("date_sent");
        LocalDateTime date = (ts != null) ? ts.toLocalDateTime() : null;
        Long replyToId = rs.getLong("reply_to_id");

        User sender = findUserById(senderId, connection);
        List<User> receivers = getRecipients(id, connection);

        if (replyToId != 0 && !rs.wasNull()) {
            Message parentMessage = internalFindOne(replyToId, connection);
            return new ReplyMessage(id, sender, receivers, text, date, parentMessage);
        } else {
            return new Message(id, sender, receivers, text, date);
        }
    }

    @Override
    public Optional<Message> findOne(Long id) {
        String sql = "SELECT * FROM messages WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return Optional.of(extractMessage(result, connection));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Message> findAll() {
        String sql = "SELECT * FROM messages ORDER BY date_sent DESC";
        List<Message> messages = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                messages.add(extractMessage(result, connection));
            }
            return messages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Message> save(Message entity) {
        String sqlMessage = "INSERT INTO messages (sender_id, text, date_sent, reply_to_id) VALUES (?, ?, ?, ?)";
        String sqlRecipients = "INSERT INTO message_recipients (message_id, recipient_id) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement psMsg = connection.prepareStatement(sqlMessage, Statement.RETURN_GENERATED_KEYS)) {

            psMsg.setLong(1, entity.getSender().getId());
            psMsg.setString(2, entity.getMessage());
            psMsg.setTimestamp(3, Timestamp.valueOf(entity.getData()));

            if (entity instanceof ReplyMessage) {
                Message parent = ((ReplyMessage) entity).getReplyMessage();
                if (parent != null) {
                    psMsg.setLong(4, parent.getId());
                } else {
                    psMsg.setNull(4, Types.BIGINT);
                }
            } else {
                psMsg.setNull(4, Types.BIGINT);
            }

            int affectedRows = psMsg.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = psMsg.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long messageId = generatedKeys.getLong(1);
                        entity.setId(messageId);

                        try (PreparedStatement psRec = connection.prepareStatement(sqlRecipients)) {
                            for (User receiver : entity.getReceivers()) {
                                psRec.setLong(1, messageId);
                                psRec.setLong(2, receiver.getId());
                                psRec.executeUpdate();
                            }
                        }
                        return Optional.of(entity);
                    }
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Message> delete(Long id) {
        if (id == null) throw new IllegalArgumentException("Id null");

        Optional<Message> found = findOne(id);
        if (found.isEmpty()) return Optional.empty();

        String sql = "DELETE FROM messages WHERE id = ?";
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
    public Optional<Message> update(Message entity) {
        String sql = "UPDATE messages SET text = ?, reply_to_id = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, entity.getMessage());

            if (entity instanceof ReplyMessage) {
                Message parent = ((ReplyMessage) entity).getReplyMessage();
                if (parent != null) {
                    statement.setLong(2, parent.getId());
                } else {
                    statement.setNull(2, Types.BIGINT);
                }
            } else {
                statement.setNull(2, Types.BIGINT);
            }

            statement.setLong(3, entity.getId());

            int response = statement.executeUpdate();
            return response == 0 ? Optional.empty() : Optional.of(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Message> findByUsername(String username) {
        return Optional.empty();
    }

    public Page<Message> findAllOnPage(Pageable pageable) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            int total = count(connection);
            List<Message> list = new ArrayList<>();
            if (total > 0) {
                String sql = "SELECT * FROM messages ORDER BY date_sent DESC LIMIT ? OFFSET ?";
                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, pageable.getPageSize());
                    stmt.setInt(2, pageable.getPageSize() * pageable.getPageNumber());
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        list.add(extractMessage(rs, connection));
                    }
                }
            }
            return new Page<>(list, total);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int count(Connection connection) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT count(*) as cnt FROM messages");
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt("cnt") : 0;
        }
    }


    public List<Message> findAllForUser(Long idUserCurent) {
        List<Message> messages = new ArrayList<>();

        String sql = "SELECT m.* FROM messages m " +
                "INNER JOIN message_recipients mr ON m.id = mr.message_id " +
                "WHERE mr.recipient_id = ? " +
                "ORDER BY m.date_sent DESC";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, idUserCurent);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    messages.add(extractMessage(resultSet, connection));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return messages;
    }
}