package org.example.domain.ducks;

import java.util.List;

/**
 * The type Card.
 */
public class Card<T extends Duck>{

    private Long id;
    private String numeCard;
    private List<T> membri;

    /**
     * Instantiates a new Card.
     *
     * @param id       the id
     * @param numeCard the nume
     * @param membri   the membri
     */
    public Card(Long id, String numeCard, List<T> membri) {
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

        double medie_performanta;

        if (this.membri == null || this.membri.isEmpty()) {
            return 0;
        }

        medie_performanta = 0;
        int nr_ducks = 0;
        for (T duck : this.membri) {
            double viteza = duck.getViteza();
            double rezistenta = duck.getRezistenta();

            if (rezistenta != 0){
                double performanta = viteza / rezistenta;
                medie_performanta += performanta;

                nr_ducks++;
            }

        }

        if (nr_ducks == 0) {
            return 0;
        }

        medie_performanta = medie_performanta / nr_ducks;

        return medie_performanta;
    }

    /**
     * Get membri list.
     *
     * @return the list
     */
    public List<T> getMembri(){
        return this.membri;
    }

    /**
     * Set membri.
     *
     * @param membri the membri
     */
    public void setMembri(List<T> membri){
        this.membri = membri;
    }

    @Override
    public String toString() {
        return "Card{" +
                "nume='" + numeCard + '\'' +
                '}';
    }
}
