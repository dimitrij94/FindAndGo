package com.example.validators;

import com.example.constants.PlaceSpecialities;
import com.example.pojo.dto.AddressDTO;
import com.example.pojo.dto.PhotoDTO;
import com.example.pojo.dto.PlaceDTO;
import com.example.services.imageservice.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Created by Dmitrij on 02.11.2015.
 */
@Component
public class PlaceRegistrationFormValidator implements Validator {

    private final Validator addressValidator;
    private final Validator photoValidator;

    @Autowired
    public PlaceRegistrationFormValidator(AddressValidator addressValidator, Validator photoValidator) {

        if (addressValidator == null || photoValidator == null) {
            throw new IllegalArgumentException("The supplied [Validator] is " +
                    "required and must not be null.");
        }
        if (!(addressValidator.supports(AddressDTO.class) || photoValidator.supports(PhotoDTO.class))) {
            throw new IllegalArgumentException("The supplied [Validator] must " +
                    "support the validation of [Address and Image] instances.");
        }
        this.photoValidator = photoValidator;
        this.addressValidator = addressValidator;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return PlaceDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "specialization", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "photo", "photo.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"address.fullAddress","field.required");
        PlaceDTO place = (PlaceDTO) target;

        if (!Pattern.compile("^[a-zA-Z][a-zA-Z0-9-_]{1,20}$").matcher(place.getName()).matches()) {
            errors.rejectValue("name", "field.invalid");
        }

        if (!(Arrays.stream(PlaceSpecialities.class.getEnumConstants())
                .anyMatch((c) -> c.getName().equals(place.getSpecialization()))))
            errors.rejectValue("speciality", "field.invalid");

        int dL = place.getDescription().length();
        if (dL == 0 || dL > 250)
            errors.rejectValue("description", "field.invalid");

        try {
            errors.pushNestedPath("photo");
            ValidationUtils.invokeValidator(this.photoValidator, place.getPhoto(), errors);
        } finally {
            errors.popNestedPath();
        }

        try {
            errors.pushNestedPath("address");
            ValidationUtils.invokeValidator(this.addressValidator, place.getAddress(), errors);
        } finally {
            errors.popNestedPath();
        }
    }

}
