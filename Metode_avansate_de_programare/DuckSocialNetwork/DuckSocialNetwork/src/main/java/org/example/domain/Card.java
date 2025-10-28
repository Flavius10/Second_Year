package org.example.domain;

/**
 * The type Card.
 */
public class Card {
    private String nume;

    /**
     * Instantiates a new Card.
     *
     * @param nume the nume
     */
    public Card(String nume) {
        this.nume = nume;
    }

    /**
     * Gets nume.
     *
     * @return the nume
     */
    public String getNume() {
        return nume;
    }

    /**
     * Sets nume.
     *
     * @param nume the nume
     */
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
