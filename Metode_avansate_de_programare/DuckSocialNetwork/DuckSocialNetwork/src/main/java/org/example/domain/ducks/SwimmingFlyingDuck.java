package org.example.domain.ducks;

import org.example.domain.TypeDuck;
import org.example.domain.card.Card;

public class SwimmingFlyingDuck extends Duck implements Inotator, Zburator{

    public SwimmingFlyingDuck(Long id, String name, String email, String password,
                      TypeDuck tip, double viteza, double rezistenta, Card card) {
        super(id, name, email, password, tip, viteza, rezistenta, card);
    }

    @Override
    public void inoata(){
        System.out.println("SwimmingFlyingDuck care inoata");
    }

    @Override
    public void zboara(){
        System.out.println("SwimmingFlyingDuck care zboara");
    }


}
