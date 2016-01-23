package com.example.validators;

import com.example.pojo.dto.PhotoDTO;
import com.example.pojo.dto.UserDTO;
import com.example.services.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

/**
 * Created by Dmitrij on 14.11.2015.
 */
@Component
public class UserValidator implements Validator {


    @Autowired
    RegistrationService service;

    @Autowired
    PhotoValidator photoValidator;

    @Autowired
    public UserValidator(PhotoValidator photoValidator) {
        if (photoValidator == null) {
            throw new IllegalArgumentException("The supplied [Validator] is " +
                    "required and must not be null.");
        }
        if (!(photoValidator.supports(PhotoDTO.class))) {
            throw new IllegalArgumentException("The supplied [Validator] must " +
                    "support the validation of [Image] instances.");
        }
        this.photoValidator = photoValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "name.required", "Поле не може бути пустим");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userEmail", "email.required", "Поле не може бути пустим");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userPass", "password.required", "Поле не може бути пустим");

        UserDTO user = (UserDTO) target;

        if (Pattern.compile("^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$").matcher(user.getName()).matches())
            errors.rejectValue("name", "field.invalid");

        if (Pattern.compile("^(?=.*\\d)(?=.*[a-zA-Z])(?!.*\\s).*$").matcher(user.getUserPass()).matches()){
            errors.rejectValue("pass","field.invalid");
        }

        if (!service.checkCredetials(user.getUserEmail(), user.getUserName()))
            errors.reject("credentials.not.unique", "Такий логін вже зайнятий");

        errors.pushNestedPath("photo");

    }
}
