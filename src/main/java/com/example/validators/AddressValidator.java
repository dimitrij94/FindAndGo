package com.example.validators;

import com.example.pojo.dto.AddressDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by Dmitrij on 02.11.2015.
 */
public class AddressValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return AddressDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"longitude","field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"latitude","field.required");
        AddressDTO address = (AddressDTO)target;

        float latitude =Float.valueOf(address.getLatitude());
        if (latitude>90||latitude<0) errors.rejectValue("latitude","field.invalid");

        float longitude = Float.valueOf(address.getLongitude());
        if(longitude>180||longitude<0) errors.rejectValue("longitude","field.invalid");

    }
}
