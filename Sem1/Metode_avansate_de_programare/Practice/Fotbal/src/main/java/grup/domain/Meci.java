package grup.domain;

public class Meci {

    private String numeEchipaGazda;
    private String numeEchipaOaspeti;
    private Integer scorEchipaGazda;
    private Integer scorEchipaOaspeti;


    public Meci(String numeEchipaGazda, String numeEchipaOaspeti,
                Integer scorEchipaGazda, Integer scorEchipaOaspeti){
        this.numeEchipaGazda = numeEchipaGazda;
        this.numeEchipaOaspeti = numeEchipaOaspeti;
        this.scorEchipaGazda = scorEchipaGazda;
        this.scorEchipaOaspeti = scorEchipaOaspeti;
    }

    public String getNumeEchipaGazda(){
        return this.numeEchipaGazda;
    }

    public String getNumeEchipaOaspeti(){
        return this.numeEchipaOaspeti;
    }

    public Integer getScorEchipaGazda(){
        return this.scorEchipaGazda;
    }

    public Integer getScorEchipaOaspeti(){
        return this.scorEchipaOaspeti;
    }



}
