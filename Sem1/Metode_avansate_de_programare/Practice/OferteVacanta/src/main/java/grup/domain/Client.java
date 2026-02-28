package grup.domain;

public class Client {
    private Long clientId;
    private String name;
    private int fidelityGrade;
    private int varsta;
    private Hobby hobby;

    public Client(Long clientId, String name, int fidelityGrade, int varsta, Hobby hobby) {
        this.clientId = clientId;
        this.name = name;
        this.fidelityGrade = fidelityGrade;
        this.varsta = varsta;
        this.hobby = hobby;
    }

    public Long getClientId() { return clientId; }
    public String getName() { return name; }
    public int getFidelityGrade() { return fidelityGrade; }
    public Hobby getHobby() { return hobby; }
}