package grup.domain;

public class Coins {

    private String nume;
    private Integer pret;

    public Coins(Integer pret, String nume) {
        this.pret = pret;
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getPret() {
        return pret;
    }

    public void setPret(Integer pret) {
        this.pret = pret;
    }
}
