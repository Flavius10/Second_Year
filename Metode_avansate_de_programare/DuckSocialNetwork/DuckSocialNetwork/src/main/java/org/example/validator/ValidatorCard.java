package org.example.validator;

import org.example.domain.ducks.card.TypeCard;

import java.lang.reflect.Type;

public class ValidatorCard implements Validation<String, String>{

    @Override
    public boolean validate(String name, String type){
        return name != null && (type.equals(TypeCard.SWIMMING) || type.equals(TypeCard.FLYING));
    }

}
