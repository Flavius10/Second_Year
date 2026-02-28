package grup.domain;

public class User {

    private String username;
    private String password;
    private UserType type;
    private Integer buget;

    public User(String username, String password, UserType type, Integer buget) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.buget = buget;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public UserType getType(){
        return this.type;
    }

    public Integer getBuget() {
        return this.buget;
    }
}
