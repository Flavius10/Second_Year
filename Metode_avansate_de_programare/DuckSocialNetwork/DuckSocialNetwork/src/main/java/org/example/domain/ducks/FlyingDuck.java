package org.example.domain.ducks;

import org.example.domain.TypeDuck;

public class FlyingDuck extends Duck implements Zburator{


    public FlyingDuck(Long id, String name, String email, String password,
                      TypeDuck tip, double viteza, double rezistenta, Card card) {
        super(id, name, email, password, tip, viteza, rezistenta, card);
    }

    @Override
    public void zboara() {
        System.out.println(getUsername() + " zboara!");
    }
}
