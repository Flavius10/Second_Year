package org.example.domain;

public class Card {
    private String nume;

    public Card(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "Card{" +
                "nume='" + nume + '\'' +
                '}';
    }
}
