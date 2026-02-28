package org.example.domain.ducks;

import org.example.domain.TypeDuck;
import org.example.domain.card.Card;

public class SwimmingDuck extends Duck implements Inotator{


    /**
     * Instantiates a new Duck.
     *
     * @param id         the id
     * @param name       the name
     * @param email      the email
     * @param password   the password
     * @param tip        the tip
     * @param viteza     the viteza
     * @param rezistenta the rezistenta
     * @param card       the card
     */
    public SwimmingDuck(Long id, String name, String email, String password, TypeDuck tip, double viteza, double rezistenta, Card card) {
        super(id, name, email, password, tip, viteza, rezistenta, card);
    }

    @Override
    public void inoata(){
        System.out.println(getUsername() + " inoata!");
    }
}
