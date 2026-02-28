package grup.domain;

public class Drone {

    private Integer id;
    private String nume_model;
    private Integer greutate_grame;
    private String tip_cadru;
    private Integer producator_id;

    public Drone(Integer id, String nume_model, Integer greutate_grame, String tip_cadru, Integer producator_id) {
        this.id = id;
        this.nume_model = nume_model;
        this.greutate_grame = greutate_grame;
        this.tip_cadru = tip_cadru;
        this.producator_id = producator_id;
    }

    public Integer getId() {
        return id;
    }

    public String getNume_model() {
        return nume_model;
    }

    public Integer getGreutate_grame() {
        return greutate_grame;
    }

    public String getTip_cadru() {
        return tip_cadru;
    }

    public Integer getProducator_id() {
        return producator_id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNume_model(String nume_model) {
        this.nume_model = nume_model;
    }

    public void setGreutate_grame(Integer greutate_grame) {
        this.greutate_grame = greutate_grame;
    }

    public void setTip_cadru(String tip_cadru) {
        this.tip_cadru = tip_cadru;
    }

    public void setProducator_id(Integer producator_id) {
        this.producator_id = producator_id;
    }

    public String toString() {
        return "Drone{" +
                "id=" + id +
                ", nume_model='" + nume_model + '\'' +
                ", greutate_grame=" + greutate_grame +
                ", tip_cadru='" + tip_cadru + '\'' +
                ", producator_id=" + producator_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Drone drone = (Drone) o;

        if (!id.equals(drone.id)) return false;
        if (!nume_model.equals(drone.nume_model)) return false;
        if (!greutate_grame.equals(drone.greutate_grame)) return false;
        if (!tip_cadru.equals(drone.tip_cadru)) return false;
        return producator_id.equals(drone.producator_id);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + nume_model.hashCode();
        result = 31 * result + greutate_grame.hashCode();
        result = 31 * result + tip_cadru.hashCode();
        result = 31 * result + producator_id.hashCode();
        return result;
    }


}
