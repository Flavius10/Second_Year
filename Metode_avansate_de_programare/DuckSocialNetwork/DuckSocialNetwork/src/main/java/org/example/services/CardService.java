package org.example.services;

import org.example.domain.ducks.card.Card;
import org.example.domain.ducks.Duck;
import org.example.repositories.repo_file.RepoFileCard;
import org.example.validator.ValidatorCard;

public class CardService {

    private RepoFileCard repoFileCard;
    private ValidatorCard validatorCard = new ValidatorCard();

    public CardService(RepoFileCard repoFileCard) {
        this.repoFileCard = repoFileCard;
    }

    public void saveCard(Card<? extends Duck> card, String file_path){
        try{
            if (validatorCard.validate(card.getNumeCard(), "FLYING"))
                throw new RuntimeException("Card is not valid!");
            this.repoFileCard.save(card, file_path);
        } catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
