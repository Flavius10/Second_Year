package org.example.domain;

import java.time.LocalDate;

/**
 * The type Persoana.
 */
public class Persoana extends User{

    private String nume;
    private String prenume;
    private String ocupatie;
    private LocalDate dataNastere;

    /**
     * Instantiates a new Persoana.
     *
     * @param id          the id
     * @param username    the username
     * @param email       the email
     * @param password    the password
     * @param nume        the nume
     * @param prenume     the prenume
     * @param ocupatie    the ocupatie
     * @param dataNastere the data nastere
     */
    public Persoana(Long id, String username, String email, String password,
                    String nume, String prenume, String ocupatie, LocalDate dataNastere) {
        super(id, username, email, password);
        this.nume = nume;
        this.prenume = prenume;
        this.ocupatie = ocupatie;
        this.dataNastere = dataNastere;
    }

    /**
     * Getters @return  the string
     */
    public String getNume(){
        return this.nume;
    }

    /**
     * Get prenume string.
     *
     * @return the string
     */
    public String getPrenume(){
        return this.prenume;
    }

    /**
     * Get ocupatie string.
     *
     * @return the string
     */
    public String getOcupatie(){
        return this.ocupatie;
    }

    /**
     * Get data nastere local date.
     *
     * @return the local date
     */
    public LocalDate getDataNastere(){
        return this.dataNastere;
    }

    /**
     * Setters @param nume the nume
     */
    public void setNume(String nume){
        this.nume = nume;
    }

    /**
     * Set prenume.
     *
     * @param prenume the prenume
     */
    public void setPrenume(String prenume){
        this.prenume = prenume;
    }

    /**
     * Set ocupatie.
     *
     * @param ocupatie the ocupatie
     */
    public void setOcupatie(String ocupatie){
        this.ocupatie = ocupatie;
    }

    /**
     * Set data nastere.
     *
     * @param dataNastere the data nastere
     */
    public void setDataNastere(LocalDate dataNastere){
        this.dataNastere = dataNastere;
    }


    @Override
    public String toString() {
        return "Persoana{" +
                "nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", ocupatie='" + ocupatie + '\'' +
                ", dataNastere=" + dataNastere +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Persoana other = (Persoana) obj;
        return other.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

}
