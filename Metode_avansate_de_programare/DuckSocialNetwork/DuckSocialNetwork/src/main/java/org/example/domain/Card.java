package org.example.domain;

/**
 * The type Card.
 */
public class Card {

    private Long id;
    private String numeCard;
    private double medie_performanta;

    /**
     * Instantiates a new Card.
     *
     * @param id       the id
     * @param numeCard the nume
     */
    public Card(Long id, String numeCard) {
        this.id = id;
        this.numeCard = numeCard;
    }

    /**
     * Gets nume.
     *
     * @return the nume
     */
    public String getNumeCard() {
        return numeCard;
    }

    /**
     * Sets nume.
     *
     * @param nume the nume
     */
    public void setNume(String nume) {
        this.numeCard = nume;
    }

    /**
     * Set id.
     *
     * @param id the id
     */
    public void setId(Long id){
        this.id = id;
    }

    /**
     * Get id long.
     *
     * @return the long
     */
    public Long getId(){
        return this.id;
    }

    /**
     * Gets medie performanta.
     *
     * @return the medie performanta
     */
    public double getMediePerformanta() {
        return this.medie_performanta;
    }

    /**
     * Sets medie performanta.
     *
     * @param medie_performanta the medie performanta
     */
    public void setMediePerformanta(double medie_performanta) {
        this.medie_performanta = medie_performanta;
    }

    @Override
    public String toString() {
        return "Card{" +
                "nume='" + numeCard + '\'' +
                '}';
    }
}
