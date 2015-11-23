package com.example.validators;

import com.example.domain.menu.PlaceMenu;
import com.example.pojo.dto.MenuDTO;
import com.example.pojo.dto.PhotoDTO;
import com.example.services.imageservice.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.regex.Pattern;

/**
 * Created by Dmitrij on 04.11.2015.
 */
@Component
public class PlaceMenuValidator implements Validator {
    private final PhotoValidator photoValidator;

    @Autowired
    public PlaceMenuValidator(PhotoValidator photoValidator) {
        if (photoValidator == null) {
            throw new IllegalArgumentException("The supplied [Validator] is " +
                    "required and must not be null.");
        }
        if (!photoValidator.supports(PhotoDTO.class)) {
            throw new IllegalArgumentException("The supplied [Validator] must " +
                    "support the validation of [Address and Image] instances.");
        }
        this.photoValidator = photoValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(MenuDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required","Заповніть поле");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "field.required","Заповніть поле");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "field.required","Заповніть поле");

        MenuDTO menu = (MenuDTO) target;

        if (menu.getPrice() > 100000 || menu.getPrice() < 0) errors.rejectValue("price", "field.invalid","Невірна ціна");

        if (menu.getHours() > 24 || menu.getHours() < 0) errors.rejectValue("hours", "field.invalid","Невірна тривалість");

        if (menu.getMinutes() > 60 || menu.getMinutes() < 0) errors.rejectValue("minutes", "field.invalid","Невірна тривалість");

        try {
            errors.pushNestedPath("photo");
            ValidationUtils.invokeValidator(this.photoValidator, menu.getPhoto(), errors);
        } finally {
            errors.popNestedPath();
        }

        PhotoDTO photo = menu.getPhoto();
        if(Math.round(photo.getW() / photo.getH())!= ImageServiceImpl.ImageSize.PLACE_PROFILE_MENU_IMAGE_SIZE.getIndex()){
            errors.rejectValue("photo", "coordinates.invalid", "Невірні координати");
        }
    }
}
