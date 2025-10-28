package org.example.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Friendship.
 */
public class Friendship {

    private Long id;
    private String first_friend_username;
    private String second_friend_username;

    /**
     * Instantiates a new Friendship.
     *
     * @param id                     the id
     * @param first_friend_username  the first friend username
     * @param second_friend_username the second friend username
     */
    public Friendship(Long id, String first_friend_username, String second_friend_username) {
        this.id = id;
        this.first_friend_username = first_friend_username;
        this.second_friend_username = second_friend_username;
    }

    /**
     * Set id.
     *
     * @param id the id
     */
    public void setId(Long id){
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets first friend username.
     *
     * @return the first friend username
     */
    public String getFirst_friend_username() {
        return this.first_friend_username;
    }

    /**
     * Gets second friend username.
     *
     * @return the second friend username
     */
    public String getSecond_friend_username() {
        return second_friend_username;
    }

    /**
     * Set first friend.
     *
     * @param first_friend_username the first friend username
     */
    public void setFirst_friend(String first_friend_username){
        this.first_friend_username = first_friend_username;
    }

    /**
     * Set second friend.
     *
     * @param second_friend the second friend
     */
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
