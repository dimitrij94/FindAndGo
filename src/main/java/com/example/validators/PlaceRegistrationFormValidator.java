package com.example.validators;

import com.example.pojo.dto.AddressDTO;
import com.example.pojo.dto.PhotoDTO;
import com.example.pojo.dto.PlaceDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by Dmitrij on 02.11.2015.
 */

public class PlaceRegistrationFormValidator implements Validator {

    private final Validator addressValidator;
    private final Validator photoValidator;

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

    private enum PlaceSpecialities {
        NightClub, Sport, Cafe;
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
        PlaceDTO place = (PlaceDTO) target;

        if (!Pattern.compile("^[a-z0-9_-]{3,15}$").matcher(place.getName()).matches()) {
            errors.rejectValue("name", "field.invalid");
        }

        if (!Arrays.asList(Arrays.stream(PlaceSpecialities.class.getEnumConstants())
                .map(PlaceSpecialities::name).toArray(String[]::new))
                .contains(place.getSpecialization()))
            errors.rejectValue("speciality", "field.invalid");

        if (Pattern.compile("[a-z0-9_-]{60,240}").matcher(place.getDescription()).matches())
            errors.rejectValue("description", "field.invalid");


        try {
            errors.pushNestedPath("address");
            ValidationUtils.invokeValidator(this.addressValidator, place.getAddress(), errors);
            errors.pushNestedPath("photo");
            ValidationUtils.invokeValidator(this.photoValidator, place.getPhoto(), errors);
        } finally {
            errors.popNestedPath();
        }

    }

}
