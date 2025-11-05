package org.example.services;

import org.example.domain.ducks.card.Card;
import org.example.domain.ducks.Duck;
import org.example.repositories.repo_file.RepoFileCard;

public class CardService {

    private RepoFileCard repoFileCard;

    public CardService(RepoFileCard repoFileCard) {
        this.repoFileCard = repoFileCard;
    }

    public void saveCard(Card<? extends Duck> card, String file_path){
        try{
            this.repoFileCard.save(card, file_path);
        } catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
