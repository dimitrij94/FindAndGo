package com.example.validators;

import com.example.pojo.dto.AddressDTO;
import com.example.pojo.dto.PhotoDTO;
import com.example.pojo.dto.UserCreateForm;
import com.example.services.registration.RegistrationService;
import com.example.services.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * Created by Dmitrij on 14.11.2015.
 */
@Component
public class UserValidator implements Validator {
    private final AddressValidator addressValidator;


    @Autowired
    RegistrationService service;

    @Autowired
    public UserValidator(AddressValidator addressValidator) {
        if (addressValidator == null) {
            throw new IllegalArgumentException("The supplied [Validator] is " +
                    "required and must not be null.");
        }
        if (!(addressValidator.supports(AddressDTO.class))) {
            throw new IllegalArgumentException("The supplied [Validator] must " +
                    "support the validation of [Address and Image] instances.");
        }
        this.addressValidator = addressValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserCreateForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "name.required", "Поле не може бути пустим");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userEmail", "email.required", "Поле не може бути пустим");
        UserCreateForm user = (UserCreateForm) target;
        if (!service.checkCredetials(user.getUserEmail(), user.getUserName()))
            errors.reject("credentials.not.unique", "Такий логін вже зайнятий");

        try {
            errors.pushNestedPath("address");
            ValidationUtils.invokeValidator(this.addressValidator, user.getAddress(), errors);
        } finally {
            errors.popNestedPath();
        }
    }
}
