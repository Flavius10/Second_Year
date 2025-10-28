package org.example.domain;

import java.util.HashMap;
import java.util.Map;

public class Friendship {

    private Long id;
    private String first_friend_username;
    private String second_friend_username;

    public Friendship(Long id, String first_friend_username, String second_friend_username) {
        this.id = id;
        this.first_friend_username = first_friend_username;
        this.second_friend_username = second_friend_username;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getFirst_friend_username() {
        return this.first_friend_username;
    }

    public String getSecond_friend_username() {
        return second_friend_username;
    }

    public void setFirst_friend(String first_friend_username){
        this.first_friend_username = first_friend_username;
    }

    public void setSecond_friend(User second_friend){
        this.second_friend_username = second_friend_username;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "first_friend=" + first_friend_username +
                ", second_friend=" + second_friend_username +
                '}';
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Friendship other = (Friendship) obj;
        return ((Friendship) obj).getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return this.getFirst_friend_username().hashCode() + this.getSecond_friend_username().hashCode();
    }
}
