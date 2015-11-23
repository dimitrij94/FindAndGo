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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required", "Вввдіть назву закладу");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "field.required", "Введіть опис закладу");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "specialization", "field.required", "Вибиберіть спеціалізацію закладу");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "photo", "photo.required", "Виберіть фото");
        PlaceDTO place = (PlaceDTO) target;

        if (!Pattern.compile("^[a-zA-Z0-9а-яА-Я_-]{3,15}$").matcher(place.getName()).matches()) {
            errors.rejectValue("name", "field.invalid", "Назва містить недопустимі символи");
        }

        if (!Arrays.asList(Arrays.stream(PlaceSpecialities.class.getEnumConstants())
                .map(PlaceSpecialities::getName).toArray(String[]::new))
                .contains(place.getSpecialization()))
            errors.rejectValue("speciality", "field.invalid", "Такої спеціальності не існує");

        if (Pattern.compile("[а-яА-Яa-zA-Z0-9_-]{60,250}").matcher(place.getDescription()).matches())
            errors.rejectValue("description", "field.invalid", "");
        ImageServiceImpl.ImageSize size = ImageServiceImpl.ImageSize.PLACE_PROFILE_IMAGE_SIZE;


        try {
            errors.pushNestedPath("address");
            ValidationUtils.invokeValidator(this.addressValidator, place.getAddress(), errors);
            errors.pushNestedPath("photo");
            ValidationUtils.invokeValidator(this.photoValidator, place.getPhoto(), errors);
        } finally {
            errors.popNestedPath();
        }
        PhotoDTO photo = place.getPhoto();
        if (Math.round(photo.getW() / photo.getH()) !=
                size.getIndex()) {
            errors.rejectValue("w", "field.invalid", "Невірні координати");
        }
    }

}
