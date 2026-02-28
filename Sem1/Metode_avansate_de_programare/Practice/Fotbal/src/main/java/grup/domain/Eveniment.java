package grup.domain;

public class Eveniment {

    public String numeEchipa;
    private Integer rataId;
    private String tipEveniment;

    public Eveniment(String numeEchipa, Integer rataId, String tipEveniment){
        this.numeEchipa = numeEchipa;
        this.tipEveniment = tipEveniment;
        this.rataId = rataId;
    }

    public void setNumeEchipa(String numeEchipa) {
        this.numeEchipa = numeEchipa;
    }

    public void setRataId(Integer rataId) {
        this.rataId = rataId;
    }

    public void setTipeEveniment(String tipeEveniment) {
        this.tipEveniment = tipeEveniment;
    }

    public String getNumeEchipa(){
        return this.numeEchipa;
    }

    public Integer getRataId(){
        return this.rataId;
    }

    public String getTipeEveniment(){
        return this.tipEveniment;
    }

}
