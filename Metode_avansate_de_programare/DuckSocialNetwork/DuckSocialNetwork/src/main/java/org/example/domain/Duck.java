package org.example.domain;

public class Duck extends User{

    private TypeDuck tip;
    private double viteza;
    private double rezistenta;
    private Card card;

    public Duck(Long id, String username, String email, String password,
                TypeDuck tip, double viteza, double rezistenta, Card card){
        super(id, username, email, password);
        this.tip = tip;
        this.viteza = viteza;
        this.rezistenta = rezistenta;
        this.card = card;
    }

}
