package org.example.domain;

import java.util.List;

/**
 * The type Card.
 */
public class Card {

    private Long id;
    private String numeCard;
    private double medie_performanta;
    private List<Duck> membri;

    /**
     * Instantiates a new Card.
     *
     * @param id       the id
     * @param numeCard the nume
     * @param membri   the membri
     */
    public Card(Long id, String numeCard, List<Duck> membri) {
        this.id = id;
        this.numeCard = numeCard;
        this.membri = membri;
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

    /**
     * Get membri list.
     *
     * @return the list
     */
    public List<Duck> getMembri(){
        return this.membri;
    }

    /**
     * Set membri.
     *
     * @param membri the membri
     */
    public void setMembri(List<Duck> membri){
        this.membri = membri;
    }

    @Override
    public String toString() {
        return "Card{" +
                "nume='" + numeCard + '\'' +
                '}';
    }
}
