package com.example.validators;

import com.example.pojo.dto.AddressDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by Dmitrij on 02.11.2015.
 */
@Component
public class AddressValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return AddressDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"longitude","field.required","Виберіть область згідно з форматом");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"latitude","field.required","Виберіть область згідно з форматом");
        AddressDTO address = (AddressDTO)target;

        float latitude = address.getLatitude();
        if (latitude>90||latitude<0) errors.rejectValue("latitude","field.invalid","Недопустима широта");

        float longitude = address.getLongitude();
        if(longitude>180||longitude<0) errors.rejectValue("longitude","field.invalid","Недопустима довгота");
    }
}
