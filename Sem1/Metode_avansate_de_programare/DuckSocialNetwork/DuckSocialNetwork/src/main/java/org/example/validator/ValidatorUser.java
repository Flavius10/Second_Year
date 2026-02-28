package org.example.validator;

import org.example.domain.User;

public class ValidatorUser implements Validation<String, String>{

    @Override
    public boolean validate(String username, String email){
        return username != null && email != null;
    }
}
