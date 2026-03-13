package grup.domain;

public class Persoana {

    private Long id_persoana;
    private String nume;
    private String prenume;
    private Integer varsta;

    public Persoana(Long id_persoana, String nume, String prenume, Integer varsta) {
        this.id_persoana = id_persoana;
        this.nume = nume;
        this.prenume = prenume;
        this.varsta = varsta;
    }

    public Long getId_persoana() {
        return id_persoana;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public Integer getVarsta() {
        return varsta;
    }

    public void setId_persoana(Long id_persoana) {
        this.id_persoana = id_persoana;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setVarsta(Integer varsta) {
        this.varsta = varsta;
    }

    @Override
    public String toString() {
        return "Persoana{" +
                "id_persoana=" + id_persoana +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", varsta=" + varsta +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Persoana persoana = (Persoana) o;

        return id_persoana != null ? id_persoana.equals(persoana.id_persoana) : persoana.id_persoana == null;
    }

    @Override
    public int hashCode() {
        return id_persoana != null ? id_persoana.hashCode() : 0;
    }
}
