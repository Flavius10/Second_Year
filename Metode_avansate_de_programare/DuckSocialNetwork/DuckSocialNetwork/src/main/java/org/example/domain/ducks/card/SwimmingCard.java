package org.example.domain.ducks.card;

import org.example.domain.ducks.Duck;
import org.example.domain.ducks.Inotator;
import org.example.domain.ducks.SwimmingDuck;

import java.util.List;

public class SwimmingCard extends Card<SwimmingDuck> implements Inotator {

    public SwimmingCard(Long id, String numeCard, List<SwimmingDuck> membri, TypeCard typeCard){
        super(id, numeCard, membri, typeCard);
    }

    @Override
    public void inoata(){
        System.out.println("Card care inoata");
    }

}
