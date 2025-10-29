package org.example.validator;

public class ValidatorFriendship implements Validation<String, String>{

    public boolean validate(String first_friend_username, String second_friend_username){
        return first_friend_username != null && second_friend_username != null;
    }

}
