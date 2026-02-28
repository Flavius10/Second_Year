package grup.domain;

public class Car {

    private String denumire;
    private String descriere;
    private Integer pret;
    private CarStatus status;
    private String comentarii;

    public Car(String denumire, String descriere, Integer pret, CarStatus status, String comentarii){
        this.denumire = denumire;
        this.descriere = descriere;
        this.pret = pret;
        this.status = status;
        this.comentarii = comentarii;
    }

    public String getDenumire(){
        return this.denumire;
    }

    public String getDescriere(){
        return this.descriere;
    }

    public Integer getPret(){return this.pret;}
    public CarStatus getStatus(){return this.status;}
    public String getString(){return this.comentarii;}
    public String getComentarii(){return this.comentarii;}

    public void setStatus(CarStatus status){
        this.status = status;
    }

    public void setComentarii(String comentarii){
        this.comentarii = comentarii;
    }

}
