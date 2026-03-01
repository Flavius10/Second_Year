package grup.domain;

public class Producator {

    private Integer id;
    private String nume;
    private String tara_origine;

    public Producator(Integer id, String nume, String tara_origine) {
        this.id = id;
        this.nume = nume;
        this.tara_origine = tara_origine;
    }

    public Integer getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getTara_origine() {
        return tara_origine;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setTara_origine(String tara_origine) {
        this.tara_origine = tara_origine;
    }

    @Override
    public String toString() {
        return "Producator{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", tara_origine='" + tara_origine + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Producator that = (Producator) o;

        if (!id.equals(that.id)) return false;
        if (!nume.equals(that.nume)) return false;
        return tara_origine != null ? tara_origine.equals(that.tara_origine) : that.tara_origine == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + nume.hashCode();
        result = 31 * result + (tara_origine != null ? tara_origine.hashCode() : 0);
        return result;
    }
}
