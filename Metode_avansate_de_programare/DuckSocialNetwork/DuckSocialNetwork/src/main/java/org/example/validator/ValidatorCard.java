package org.example.validator;

import org.example.domain.card.TypeCard;

public class ValidatorCard implements Validation<String, String>{

    @Override
    public boolean validate(String name, String type){
        if (name == null) return false;
        try {
            TypeCard cardType = TypeCard.valueOf(type.toUpperCase());
            return cardType == TypeCard.SWIMMING || cardType == TypeCard.FLYING;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
