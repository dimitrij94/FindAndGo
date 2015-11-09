package com.example.validators;

import com.example.domain.menu.PlaceMenu;
import com.example.pojo.dto.MenuDTO;
import com.example.pojo.dto.PhotoDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created by Dmitrij on 04.11.2015.
 */
public class PlaceMenuValidator implements Validator {
    private  final PhotoValidator photoValidator;

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
        return clazz.isAssignableFrom(PlaceMenu.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "field.required");

        MenuDTO menu = (MenuDTO) target;
        CommonsMultipartFile image = menu.getPhoto().getImage();

        if (image.isEmpty()) {
            errors.rejectValue("image", "photo.required");
        }

        if (!image.getContentType().split("/")[0].equals("image")) {
            errors.rejectValue("image", "field.type.invalid");
        }
        try {
            errors.pushNestedPath("photo");
            ValidationUtils.invokeValidator(this.photoValidator, menu.getPhoto(), errors);
        } finally {
            errors.popNestedPath();
        }
    }
}
