package com.example.validators;

import com.example.pojo.dto.OwnerDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by Dmitrij on 08.02.2016.
 */
@Component
public class PlaceOwnerValidator implements Validator {

    private static String fieldRequiredMessage = "field.required";
    private static String fieldInvalidMessage = "field.invalid";

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(OwnerDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", fieldRequiredMessage);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", fieldRequiredMessage);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", fieldRequiredMessage);

        OwnerDTO owner = (OwnerDTO) target;

        if (owner.getPassword().length() < 5) errors.rejectValue("password", fieldInvalidMessage);
        if(owner.getName().length()<2) errors.rejectValue("name", fieldInvalidMessage);

    }
}
