package org.example.domain;

import java.util.HashMap;
import java.util.Map;

public class Friendship {

    private User first_friend;
    private User second_friend;

    public Friendship(User first_friend, User second_friend) {
        this.first_friend = first_friend;
        this.second_friend = second_friend;
    }

    public User getFirst_friend() {
        return first_friend;
    }

    public User getSecond_friend() {
        return second_friend;
    }

    public void setFirst_friend(User first_friend){
        this.first_friend = first_friend;
    }

    public void setSecond_friend(User second_friend){
        this.second_friend = second_friend;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "first_friend=" + first_friend +
                ", second_friend=" + second_friend +
                '}';
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Friendship other = (Friendship) obj;
        return other.getFirst_friend().equals(this.getFirst_friend()) && other.getSecond_friend().equals(this.getSecond_friend());
    }

    @Override
    public int hashCode() {
        return this.getFirst_friend().hashCode() + this.getSecond_friend().hashCode();
    }
}
