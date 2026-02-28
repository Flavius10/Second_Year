package org.example.services;

import org.example.domain.card.Card;
import org.example.domain.ducks.Duck;
import org.example.repositories.repo_db.RepoDBCard;
import org.example.validator.ValidatorCard;

import java.util.Optional;

public class CardService {

    private RepoDBCard<Duck> repoDBCard;
    private ValidatorCard validatorCard = new ValidatorCard();

    public CardService(RepoDBCard<Duck> repoDBCard) {
        this.repoDBCard = repoDBCard;
    }

    public void saveCard(Card<? extends Duck> card){
        try {

            if (!validatorCard.validate(card.getNumeCard(), card.getTypeCard().name())) {
                throw new RuntimeException("Card is not valid!");
            }

            Optional<Card<Duck>> saved = repoDBCard.save((Card<Duck>) card);
            if (saved.isPresent()) {
                throw new RuntimeException("Card already exists!");
            }

            System.out.println("Card salvat cu succes!");

        } catch (Exception e) {
            throw new RuntimeException("Eroare la salvarea cardului: " + e.getMessage());
        }
    }
}
