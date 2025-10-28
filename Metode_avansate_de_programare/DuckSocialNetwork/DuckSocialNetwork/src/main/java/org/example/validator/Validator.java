package org.example.validator;

public class Validator {

    public static boolean validateFriendship(String first_friend_username, String second_friend_username){
        return first_friend_username != null && second_friend_username != null;
    }

}
