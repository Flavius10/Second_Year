package org.example.domain.card;

import org.example.domain.ducks.FlyingDuck;
import org.example.domain.ducks.Zburator;

import java.util.List;

public class FlyingCard extends Card<FlyingDuck> implements Zburator {

    public FlyingCard(Long id, String numeCard, List<FlyingDuck> membri, TypeCard typeCard){
        super(id, numeCard, membri, typeCard);
    }

    @Override
    public void zboara(){
        System.out.println("Card care zboara");
    }

}
