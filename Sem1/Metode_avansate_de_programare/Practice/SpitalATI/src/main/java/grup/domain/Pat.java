package grup.domain;

public class Pat {

    private Integer id;
    private String tip;
    private boolean ventilatie;
    private String cnpPacient;

    public Pat(Integer id, String tip, boolean ventilatie, String cnpPacient) {
        this.id = id;
        this.tip = tip;
        this.ventilatie = ventilatie;
        this.cnpPacient = cnpPacient;
    }

    public boolean isLiber(){
        return this.cnpPacient == null || this.cnpPacient.isEmpty();
    }

    public int getId() {
        return id;
    }

    public String getTip() {
        return tip;
    }

    public boolean isVentilatie() {
        return ventilatie;
    }

    public String getCnpPacient() {
        return cnpPacient;
    }

    public void setCnpPacient(String cnpPacient) {
        this.cnpPacient = cnpPacient;
    }

    public void setDescriere(String tip) {
        this.tip = tip;
    }

    public void setVentilatie(boolean ventilatie) {
        this.ventilatie = ventilatie;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Pat{" +
                "id=" + id +
                ", descriere='" + tip + '\'' +
                ", ventilatie=" + ventilatie +
                ", cnpPacient='" + cnpPacient + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pat pat = (Pat) o;

        return id != null ? id.equals(pat.id) : pat.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
