package org.example.domain;

import java.time.LocalDate;

public class Persoana extends User{

    private String nume;
    private String prenume;
    private String ocupatie;
    private LocalDate dataNastere;

    public Persoana(Long id, String username, String email, String password,
                    String nume, String prenume, String ocupatie, LocalDate dataNastere) {
        super(id, username, email, password);
        this.nume = nume;
        this.prenume = prenume;
        this.ocupatie = ocupatie;
        this.dataNastere = dataNastere;
    }

    /// Getters
    public String getNume(){
        return this.nume;
    }

    public String getPrenume(){
        return this.prenume;
    }

    public String getOcupatie(){
        return this.ocupatie;
    }

    public LocalDate getDataNastere(){
        return this.dataNastere;
    }

    /// Setters
    public void setNume(String nume){
        this.nume = nume;
    }

    public void setPrenume(String prenume){
        this.prenume = prenume;
    }

    public void setOcupatie(String ocupatie){
        this.ocupatie = ocupatie;
    }

    public void setDataNastere(LocalDate dataNastere){
        this.dataNastere = dataNastere;
    }



}
