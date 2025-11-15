package org.example.repositories.repo_db;

import org.example.domain.card.Card;
import org.example.domain.card.SwimmingCard;
import org.example.domain.card.FlyingCard;
import org.example.domain.card.TypeCard;
import org.example.domain.ducks.Duck;
import org.example.utils.paging.Page;
import org.example.utils.paging.Pageable;

import java.sql.*;
import java.util.*;

public class RepoDBCard<E extends Duck> implements RepoDB<Long, Card<E>> {

    private String url;
    private String username;
    private String password;

    public RepoDBCard(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<Card<E>> findOne(Long id) {
        String query = "SELECT * FROM card WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String numeCard = result.getString("numecard");
                String typeCardStr = result.getString("typecard");
                TypeCard typeCard = TypeCard.valueOf(typeCardStr.toUpperCase());

                Card<E> card;
                if (typeCard == TypeCard.SWIMMING) {
                    card = (Card<E>) new SwimmingCard(id, numeCard, new ArrayList<>(), typeCard);
                } else {
                    card = (Card<E>) new FlyingCard(id, numeCard, new ArrayList<>(), typeCard);
                }
                return Optional.of(card);
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Card<E>> findAll() {
        String query = "SELECT * FROM card";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet result = statement.executeQuery()) {

            List<Card<E>> cards = new ArrayList<>();
            while (result.next()) {
                Long id = result.getLong("id");
                String numeCard = result.getString("numecard");
                String typeCardStr = result.getString("typecard");
                TypeCard typeCard = TypeCard.valueOf(typeCardStr.toUpperCase());

                Card<E> card;
                if (typeCard == TypeCard.SWIMMING) {
                    card = (Card<E>) new SwimmingCard(id, numeCard, new ArrayList<>(), typeCard);
                } else {
                    card = (Card<E>) new FlyingCard(id, numeCard, new ArrayList<>(), typeCard);
                }
                cards.add(card);
            }
            return cards;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Card<E>> save(Card<E> entity) {
        String insertSQL = "INSERT INTO card (numecard, typecard) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getNumeCard());
            statement.setString(2, entity.getTypeCard().name());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                return Optional.of(entity);
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Card<E>> delete(Long id) {
        String deleteSQL = "DELETE FROM card WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(deleteSQL)) {

            Optional<Card<E>> existing = findOne(id);
            int affected = 0;
            if (existing.isPresent()) {
                statement.setLong(1, id);
                affected = statement.executeUpdate();
            }

            return affected == 0 ? Optional.empty() : existing;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Card<E>> update(Card<E> entity) {
        String updateSQL = "UPDATE card SET numecard = ?, typecard = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(updateSQL)) {

            statement.setString(1, entity.getNumeCard());
            statement.setString(2, entity.getTypeCard().name());
            statement.setLong(3, entity.getId());

            int affectedRows = statement.executeUpdate();
            return affectedRows == 0 ? Optional.of(entity) : Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Card<E>> findByUsername(String username) {
        // Nu are sens pentru Card, putem returna Optional.empty()
        return Optional.empty();
    }

    @Override
    public Page<Card<E>> findAllOnPage(Pageable pageable) {
        String countSQL = "SELECT COUNT(*) as count FROM card";
        String selectSQL = "SELECT * FROM card LIMIT ? OFFSET ?";
        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            int total = 0;
            try (PreparedStatement psCount = connection.prepareStatement(countSQL);
                 ResultSet rsCount = psCount.executeQuery()) {
                if (rsCount.next()) {
                    total = rsCount.getInt("count");
                }
            }

            List<Card<E>> cards = new ArrayList<>();
            try (PreparedStatement ps = connection.prepareStatement(selectSQL)) {
                ps.setInt(1, pageable.getPageSize());
                ps.setInt(2, pageable.getPageNumber() * pageable.getPageSize());
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Long id = rs.getLong("id");
                        String numeCard = rs.getString("numecard");
                        TypeCard typeCard = TypeCard.valueOf(rs.getString("typecard"));

                        Card<E> card;
                        if (typeCard == TypeCard.SWIMMING) {
                            card = (Card<E>) new SwimmingCard(id, numeCard, new ArrayList<>(), typeCard);
                        } else {
                            card = (Card<E>) new FlyingCard(id, numeCard, new ArrayList<>(), typeCard);
                        }

                        cards.add(card);
                    }
                }
            }

            return new Page<>(cards, total);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
