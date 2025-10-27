package org.example.domain;

public class Duck extends User{

    private TypeDuck tip;
    private double viteza;
    private double rezistenta;
    private Card card;

    public Duck(Long id, String name, String email, String password,
                TypeDuck tip, double viteza, double rezistenta, Card card){
        super(id, name, email, password);
        this.tip = tip;
        this.viteza = viteza;
        this.rezistenta = rezistenta;
        this.card = card;
    }

    /// Getters
    public TypeDuck getTip() {
        return this.tip;
    }

    public double getViteza() {
        return this.viteza;
    }

    public double getRezistenta() {
        return this.rezistenta;
    }

    public Card getCard() {
        return this.card;
    }

    /// Setters
    public void setTip(TypeDuck tip) {
        this.tip = tip;
    }

    public void setViteza(double viteza) {
        this.viteza = viteza;
    }

    public void setRezistenta(double rezistenta) {
        this.rezistenta = rezistenta;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "Duck{" +
                "tip=" + tip +
                ", viteza=" + viteza +
                ", rezistenta=" + rezistenta +
                ", card=" + card +
                '}';
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Duck other = (Duck) obj;
        return other.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

}
